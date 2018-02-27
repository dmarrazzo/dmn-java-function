package client;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNResult;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.DMNServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Person;

public class Main {
	final static Logger log = LoggerFactory.getLogger(Main.class);

	private static final String URL = "http://localhost:8080/kie-server/services/rest/server";
	private static final String user = "donato";
	private static final String password = "donato";
	private static final String CONTAINER = "dmn-function_1.0.0";

	private static final String NAMESPACE = "http://www.trisotech.com/definitions/_392573d3-b0fb-4118-b40e-b8da2aba9c23";
	private static final String MODEL_NAME = "JavaInvocation";

	private DMNServicesClient dmnClient;

	public Main() {
		setup();
	}

	public static void main(String[] args) {
		Main main = new Main();

		long start = System.currentTimeMillis();
		main.executeDMN();
		long end = System.currentTimeMillis();
		System.out.println("elapsed time: " + (end - start));
	}

	private void executeDMN() {
		DMNContext dmnContext = dmnClient.newContext();

		Person person = new Person("donato", 33, "italy");

		dmnContext.set("Input", person);
		ServiceResponse<DMNResult> serverResp = dmnClient.evaluateAll(CONTAINER, NAMESPACE, MODEL_NAME, dmnContext);

		DMNResult dmnResult = serverResp.getResult();
		DMNDecisionResult result = dmnResult.getDecisionResultByName("What the result of this Java");
		System.out.println(result.getResult());
	}

	private void setup() {
		KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(URL, user, password);
		// Marshalling
		Set<Class<?>> extraClasses = new HashSet<>();
		extraClasses.add(Person.class);
		config.addExtraClasses(extraClasses);
		config.setMarshallingFormat(MarshallingFormat.JSON);
		Map<String, String> headers = null;
		config.setHeaders(headers);

		KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
		dmnClient = client.getServicesClient(DMNServicesClient.class);
	}

}
