package reivajh06.ai;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Ollama {

	public static String generate(String model, String prompt) {
		return generate(model, prompt, 0.25);
	}

	public static String generate(String model, String prompt, double temperature) {
		return generate(model, prompt, "", temperature);
	}

	public static String generate(String model, String prompt, String systemPrompt, double temperature) {
		try(HttpClient client = HttpClient.newHttpClient()) {

			HttpResponse<String> response = client.send(
					buildRequest(model, prompt, systemPrompt, temperature),
					HttpResponse.BodyHandlers.ofString(UTF_8)
			);

			return textOf(response.body());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static HttpRequest buildRequest(String model, String prompt, String systemPrompt, double temperature) {
		return HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:11434/api/generate"))
				.POST(HttpRequest.BodyPublishers.ofString(body(model, prompt, systemPrompt, temperature)))
				.build();
	}

	private static String textOf(String json) {
		Matcher matcher = Pattern.compile("\"response\":\"((?:\\\\\"|[^\"])*?)\"").matcher(json);

		if (matcher.find()) return matcher.group(1).replace("\\\"", "\"").replace("\\n", "\n");

		throw new RuntimeException("LLM did not respond");
	}

	private static String body(String model, String prompt, String systemPrompt, double temperature) {
		return "{\n" +
				"  \"model\": \"" + escapeJson(model) + "\",\n" +
				"  \"prompt\": \"" + escapeJson(prompt) + "\",\n" +
				"  \"system\": \"" + escapeJson(systemPrompt) + "\",\n" +
				"  \"options\": {\"temperature\": " + temperature + "},\n" +
				"  \"stream\": false\n" +
				"}";
	}

	private static String escapeJson(String input) {
		if (input == null) return "";
		return input.replace("\\", "\\\\")
				.replace("\"", "\\\"")
				.replace("\b", "\\b")
				.replace("\f", "\\f")
				.replace("\n", "\\n")
				.replace("\r", "\\r")
				.replace("\t", "\\t");
	}

}
