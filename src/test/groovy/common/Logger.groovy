package common
import org.codehaus.groovy.runtime.StackTraceUtils
class Logger{

    String pre


    public Logger(def owner="NONE"){
        pre = owner in String ? owner : String.valueOf(owner.getClass())
    }
	
	boolean isDoLog(){
        return System.properties["do_log"] == "true"
    }

    void log(String msg, Throwable t = null){
    	if(isDoLog()){
            println "${pre} - LOG: ${msg}"
            if(t){
            	StackTraceUtils.deepSanitize(t).printStackTrace()
            }
     	}
    }

}