package tests
import pages.*

class BehaviorProvider{

    static Map getBehaviors(){
        return [

            "google": { String query ->
                to(GooglePage, pref_id:"167835042-afba5163-5b67-4cba-9c71-3e217bf81835")

                email.value(query)
                paymentMethod.value("mercantil")
            },

            "yahoo":{ String query ->
                to(GooglePage, pref_id: "167834996-a78aa4d8-50e2-45cc-93c9-e44d825a0fa0")

                email.value(query)
                paymentMethod.value("nativa")
            }
        ]
    }

}
