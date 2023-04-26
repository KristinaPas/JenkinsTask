const fs = require('fs')
const puppeteer = require('puppeteer')
const lighthouse = require('lighthouse/lighthouse-core/fraggle-rock/api.js')

const waitTillHTMLRendered = async (page, timeout = 30000) => {
  const checkDurationMsecs = 1000;
  const maxChecks = timeout / checkDurationMsecs;
  let lastHTMLSize = 0;
  let checkCounts = 1;
  let countStableSizeIterations = 0;
  const minStableSizeIterations = 3;

  while(checkCounts++ <= maxChecks){
    let html = await page.content();
    let currentHTMLSize = html.length; 

    let bodyHTMLSize = await page.evaluate(() => document.body.innerHTML.length);

    //console.log('last: ', lastHTMLSize, ' <> curr: ', currentHTMLSize, " body html size: ", bodyHTMLSize);

    if(lastHTMLSize != 0 && currentHTMLSize == lastHTMLSize) 
      countStableSizeIterations++;
    else 
      countStableSizeIterations = 0; //reset the counter

    if(countStableSizeIterations >= minStableSizeIterations) {
      console.log("Fully Rendered Page: " + page.url());
      break;
    }

    lastHTMLSize = currentHTMLSize;
    await page.waitForTimeout(checkDurationMsecs);
  }  
};

async function captureReport() {
	const browser = await puppeteer.launch({args: ['--allow-no-sandbox-job', '--allow-sandbox-debugging', '--no-sandbox', '--disable-gpu', '--disable-gpu-sandbox', '--display', '--ignore-certificate-errors', '--disable-storage-reset=true']});
	//const browser = await puppeteer.launch({args: ['--allow-no-sandbox-job', '--allow-sandbox-debugging', '--no-sandbox', '--disable-gpu', '--disable-gpu-sandbox', '--display', '--ignore-certificate-errors', '--disable-storage-reset=true']});
	const page = await browser.newPage();
	
	await page.setViewport({"width":1920,"height":1080});
	await page.setDefaultTimeout(10000);
	
	const navigationPromise = page.waitForNavigation({timeout: 30000, waitUntil: ['domcontentloaded']});
	await page.goto("http://localhost");
    await navigationPromise;
		
	const flow = await lighthouse.startFlow(page, {
		name: 'demoblaze',
		configContext: {
		  settingsOverrides: {
			throttling: {
			  rttMs: 40,
			  throughputKbps: 10240,
			  cpuSlowdownMultiplier: 1,
			  requestLatencyMs: 0,
			  downloadThroughputKbps: 0,
			  uploadThroughputKbps: 0
			},
			throttlingMethod: "simulate",
			screenEmulation: {
			  mobile: false,
			  width: 1920,
			  height: 1080,
			  deviceScaleFactor: 1,
			  disabled: false,
			},
			formFactor: "desktop",
			onlyCategories: ['performance'],
		  },
		},
	});

  	//================================NAVIGATE================================
   await flow.navigate("http://localhost", {
		stepName: 'open main page'
		});
  	console.log('main page is opened');
	
	//================================SELECTORS================================
	const tablesTab      = "a[data-rb-event-key='tables'].nav-link";
	const tableProductCart = "div.tab-pane.active div.title-price-wrap-2 a[href='/product/olive-table']";
	const AddTableToCart = "div.pro-details-cart button";
	const CartIsUpdated = "div.react-toast-notifications__toast--success";
	const openCart  = "a[href='/cart'].icon-cart";	
	const proceedToCheckout = "div.box-custom a[href='/checkout']"
	const billingInfo = "div.billing-info-wrap"
	
	//================================PAGE_ACTIONS================================
	await flow.startTimespan({ stepName: 'tablesTab' });
		await page.waitForSelector("a[data-rb-event-key='tables'].nav-link")
		await page.click("a[data-rb-event-key='tables'].nav-link")
        await waitTillHTMLRendered(page);
		await page.waitForSelector("div.tab-pane.active div.title-price-wrap-2 a[href='/product/olive-table']")
    await flow.endTimespan();
    console.log('tablesTab is completed');
	
	await flow.startTimespan({ stepName: 'tableProductCart' });
		await page.waitForSelector("div.tab-pane.active div.title-price-wrap-2 a[href='/product/olive-table']")
		await page.click("div.tab-pane.active div.title-price-wrap-2 a[href='/product/olive-table']")
		await waitTillHTMLRendered(page);
		await page.waitForSelector("div.pro-details-cart button")
	await flow.endTimespan();
	console.log('tableProductCart is completed');
	
	await flow.startTimespan({ stepName: 'AddTableToCart' });
		await page.waitForSelector("div.pro-details-cart button")
		await page.click("div.pro-details-cart button")
		await waitTillHTMLRendered(page);
		await page.waitForSelector("div.react-toast-notifications__toast--success")
	await flow.endTimespan();
	console.log('AddTableToCart is completed');	
	
	await flow.startTimespan({ stepName: 'openCart' });
		await page.waitForSelector("a[href='/cart'].icon-cart")
		//await page.click("a.default-btn") ????THIS STEP GIVES AN ERROR "Node is not clickable"????
		await page.goto("http://localhost/cart")
		await waitTillHTMLRendered(page);
		await page.waitForSelector("div.box-custom a[href='/checkout']")
	await flow.endTimespan();
	console.log('openCart is completed');
	
	await flow.startTimespan({ stepName: 'proceedToCheckout' });
		await page.waitForSelector("div.box-custom a[href='/checkout']")
		await page.click("div.box-custom a[href='/checkout']")
		await waitTillHTMLRendered(page);
		await page.waitForSelector("div.billing-info-wrap")
	await flow.endTimespan();
	console.log('proceedToCheckout is completed');

	//================================REPORTING================================
	const reportPath = __dirname + '/user-flow.report.html';
	//const reportPathJson = __dirname + '/user-flow.report.json';

	const report = flow.generateReport();
	//const reportPathJson = JSON.stringify(flow.getFlowResult()).replace(/</g, '\\u003c').replace(/|u2028/g, '\\u2028').replace(/\u2029/g, '\\u2029');
	
	fs.writeFileSync(reportPath, await report);
	//fs.writeFileSync(reportPathJson, reportJson);
	
	
    await browser.close();
}
captureReport();