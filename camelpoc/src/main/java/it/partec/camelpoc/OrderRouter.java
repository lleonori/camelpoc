package it.partec.camelpoc;

import org.apache.camel.language.XPath;

public class OrderRouter {
    public String routeOrder(@XPath(value = "/regola/canale/text()") String channel, @XPath(value = "/regola/importoLimite/text()") Double amount) {
        if ("Online".equals(channel)) {
            return "direct:onlineOrder";
        } else if ("Telefono".equals(channel)) {
            return "direct:phoneOrder";
        } else if (amount != null && amount > 500) {
            return "direct:vipOrder";
        } else {
            return "direct:errorOrder";
        }
    }
}
