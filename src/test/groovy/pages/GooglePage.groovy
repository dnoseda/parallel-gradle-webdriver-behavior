package pages
import geb.*

class GooglePage extends Page{

    static url = "https://www.mercadopago.com/beta/checkout/pay"

    String converToPath(Object[] args) {
        args ? '&' + args*.toString().join('&') : ""
    }

    static at = {
        $("input#email")
    }

    static content = {

        email (required:false) { $('#email') }
        paymentMethod{ $("select#pmtOption") }

    }
}
