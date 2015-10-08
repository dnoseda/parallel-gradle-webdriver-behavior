/*
 This is the Geb configuration file.
 See: http://www.gebish.org/manual/current/configuration.html
 */


//import org.openqa.selenium.firefox.FirefoxDriver

import org.openqa.selenium.chrome.*
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.saucelabs.common.SauceOnDemandAuthentication;
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService;
import org.codehaus.groovy.runtime.StackTraceUtils

waiting {
    timeout = 10
    retryInterval = 0.5
    presets {
        slow {
            timeout = 20
            retryInterval = 1
        }
        superslow {
            timeout = 180
            retryInterval = 1
        }
        quick {
            timeout = 1
        }
    }
}

def generateDriver(DesiredCapabilities capabilities, Closure defaultGenerator){
	def remote = System.properties["remote"]
	WebDriver driver
	if(remote){
		def remoteUrl = remote == "true" ? "<some-default-ip>" : remote
        // TODO: validate IP
		driver = new RemoteWebDriver( new URL("http://${remoteUrl}:4444/wd/hub"), capabilities);
	}else if(defaultGenerator){
		driver = defaultGenerator(capabilities)
	}
	return driver
}

/**/
environments {

	chrome {
		driver = {
			def chOp = new ChromeOptions()
			chOp.addArguments("--user-agent=Mozilla/5.0 (X11; Linux i686 (x86_64)) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36")
			chOp.addArguments("--disable-web-security")
			DesiredCapabilities capabilities = DesiredCapabilities.chrome()
			capabilities.setCapability(ChromeOptions.CAPABILITY, chOp)
			

			
			return generateDriver(capabilities,  { cap -> new ChromeDriver(cap) })

		}
		
	}
	
	
	phantom {
		driver = {
			def chOp = new ChromeOptions()
			
			chOp.addArguments("--user-agent=Mozilla/5.0 (iphone; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25")
			//println "Executing in phantom js"
			chOp.addArguments("--disable-web-security")
			DesiredCapabilities capabPhantom = DesiredCapabilities.chrome()
			capabPhantom.setCapability(ChromeOptions.CAPABILITY, chOp)
			capabPhantom.setCapability("name", name);
			driver = new PhantomJSDriver(capabPhantom)
		}
		cacheDriverPerThread = true
	}


	mobile {
		driver = {
			def chOp = new ChromeOptions()
			chOp.addArguments("--user-agent=Mozilla/5.0 (iphone; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25")
			chOp.addArguments("--disable-web-security")
			chOp.addArguments("--window-size=320,480")
			DesiredCapabilities capabMobile = DesiredCapabilities.chrome()
			capabMobile.setCapability(ChromeOptions.CAPABILITY, chOp)
			capabMobile.setCapability("name", name);
			return generateDriver(capabMobile, { cap -> new ChromeDriver(cap) })
		}
		cacheDriverPerThread = true
	}


}
