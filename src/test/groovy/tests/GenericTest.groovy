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
    String mySearcher
    public GenericTest(String name, String searcher) {
        myName = name
        mySearcher = searcher
    }

    @Test
    void googleIt(){
        try{
            /**/
            def behaviors = BehaviorProvider.getBehaviors() 
            def closure = behaviors[mySearcher]
            closure.resolveStrategy = Closure.DELEGATE_FIRST
            closure.delegate = this
            closure(myName)
            /** /

            go "https://www.mercadopago.com/beta/checkout/pay?pref_id=167834996-a78aa4d8-50e2-45cc-93c9-e44d825a0fa0"
            waitFor{$("input#email")}

            $("input#email").value(myName)
            $("select#pmtOption").value("nativa")
            /**/

        }catch(Throwable e){
            throw StackTraceUtils.deepSanitize(e)
        }
    }

    @Parameters
    static data(){
        def tests = []

        ["yahoo","google"].each{searcher->
                ["dulce de leche", "caramelo"].each({ query ->
                    tests << ([query, searcher] as Object[])
                })
        }

        return tests
        //return [(["hola","google"] as Object[])]
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

