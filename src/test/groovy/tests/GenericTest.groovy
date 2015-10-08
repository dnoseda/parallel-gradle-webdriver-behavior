package tests

import java.text.DecimalFormatSymbols
import java.text.DecimalFormat
import java.text.NumberFormat
import com.mercadopago.MP
import com.saucelabs.common.SauceOnDemandAuthentication
import com.saucelabs.common.SauceOnDemandSessionIdProvider
import com.saucelabs.junit.Parallelized
import com.saucelabs.junit.SauceOnDemandTestWatcher
import common.*
import geb.Configuration
import geb.ConfigurationLoader
import geb.junit4.GebReportingTest
import groovy.json.JsonOutput
import groovyx.net.http.Method
import org.codehaus.groovy.runtime.StackTraceUtils
import org.codehaus.jettison.json.JSONObject
import org.junit.AfterClass
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.openqa.selenium.remote.RemoteWebDriver
import org.junit.Test
import org.junit.runners.Parameterized.Parameters


@RunWith(Parallelized.class)
class GenericTest extends GebReportingTest implements SauceOnDemandSessionIdProvider {


    @Delegate Logger logger = new Logger(this)


    geb.Browser createBrowser() {
        geb.driver.CachingDriverFactory.clearCacheAndQuitDriver()
        return super.createBrowser()
    }


    String myName
    public GenericTest(String name) {
        myName = name
    }

    @Test
    void googleIt(){
        go "http://www.google.com?q=${myName.replaceAll(/\s/,'+')}"
    }

    @Parameters
    static data(){
        return ["dulce de leche", "caramelo"].collect({
            [it] as Object[]
        })
    }

    protected String sessionId;
    @Override
    public String getSessionId() {
        return sessionId
    }


    @Before
    void setUp() throws Exception {
        //stoping clear driver cookies
        browser.config.autoClearCookies = true

        /** /
        if (dType == "saucelabs") {
            this.sessionId = ((RemoteWebDriver) driver).getSessionId().toString();

        }
        /**/

    }

    def threadId = Thread.currentThread().getId()

    Configuration createConf() {
        Configuration conf = new ConfigurationLoader(gebConfEnv, System.properties, new GroovyClassLoader(getClass().classLoader)).getConf(gebConfScript)

        conf.reportsDir = new File("build/reports/test/testReportFor-${threadId}")

        conf.cacheDriverPerThread = true
        return conf
    }

}

