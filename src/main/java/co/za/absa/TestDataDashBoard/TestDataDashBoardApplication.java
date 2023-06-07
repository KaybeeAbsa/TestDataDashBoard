package co.za.absa.TestDataDashBoard;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@SpringBootApplication
@ComponentScan(basePackages={"co.za.absa.TestDataDashBoard","co.za.absa.TestDataDashBoard.model","co.za.absa.TestDataDashBoard.Methods","co.za.absa.TestDataDashBoard.services","co.za.absa.TestDataDashBoard.controller","co.za.absa.TestDataDashBoard.repositories"})
public class TestDataDashBoardApplication  extends SpringBootServletInitializer {

//	@Value("${server.ssl.trust-store}")
//	private Resource resource;

	public static void main(String[] args) {

		SpringApplicationBuilder builder = new SpringApplicationBuilder(TestDataDashBoardApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		builder.headless(false);
		return builder.sources(TestDataDashBoardApplication.class);
	}

	@PostConstruct
	public void configureSsl() throws IOException {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[]{tm}, null);
			SSLContext.setDefault(ctx);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	@Bean
	public RestTemplate getRestTemplate()
	{
		RestTemplate restTemplate = new RestTemplate();

		return restTemplate;
	}



}
