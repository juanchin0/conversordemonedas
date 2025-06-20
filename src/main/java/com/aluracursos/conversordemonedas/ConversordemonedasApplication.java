package com.aluracursos.conversordemonedas;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import java.util.InputMismatchException; // Para manejar errores de entrada si el usuario no pone un número
import java.util.Scanner; // ¡Nueva importación para leer la entrada del usuario!

public class ConversordemonedasApplication {

	private static final String API_KEY = "72b4b867feccc14dbe7a3a6d";
	private static final String BASE_API_URL = "https://v6.exchangerate-api.com/v6/";

	public static void main(String[] args) {
		// Objeto Scanner para leer la entrada del usuario
		Scanner scanner = new Scanner(System.in);
		Gson gson = new Gson(); // Instancia de Gson una vez

		System.out.println("*************************************************");
		System.out.println("        Bienvenido al Conversor de Monedas");
		System.out.println("*************************************************");

		boolean running = true;
		while (running) {
			System.out.println("\nSeleccione una opción:");
			System.out.println("1. Convertir moneda (Ej. USD a MXN)");
			System.out.println("2. Salir");
			System.out.print("Ingrese su opción: ");

			try {
				int option = scanner.nextInt();
				scanner.nextLine(); // Consume la nueva línea restante después de nextInt()

				switch (option) {
					case 1:
						System.out.print("Ingrese la moneda de origen (ej. USD, EUR, JPY): ");
						String fromCurrency = scanner.nextLine().toUpperCase(); // Convertir a mayúsculas para la API

						System.out.print("Ingrese la moneda de destino (ej. MXN, GBP, CAD): ");
						String toCurrency = scanner.nextLine().toUpperCase(); // Convertir a mayúsculas

						System.out.print("Ingrese la cantidad a convertir: ");
						double amountToConvert = scanner.nextDouble();
						scanner.nextLine(); // Consume la nueva línea

						// Llama al método para realizar la conversión
						performConversion(fromCurrency, toCurrency, amountToConvert, gson);
						break;
					case 2:
						System.out.println("¡Gracias por usar el conversor! Saliendo...");
						running = false;
						break;
					default:
						System.out.println("Opción no válida. Por favor, intente de nuevo.");
						break;
				}
			} catch (InputMismatchException e) {
				System.err.println("Entrada no válida. Por favor, ingrese un número para la opción o cantidad.");
				scanner.nextLine(); // Limpiar el buffer del scanner
			} catch (Exception e) {
				System.err.println("Ocurrió un error inesperado: " + e.getMessage());
				e.printStackTrace();
			}
		}

		scanner.close(); // Es importante cerrar el scanner al finalizar
	}

	// Método para encapsular la lógica de la conversión
	private static void performConversion(String fromCurrency, String toCurrency, double amountToConvert, Gson gson) {
		try {
			String requestUrl = BASE_API_URL + API_KEY + "/pair/" + fromCurrency + "/" + toCurrency;
			System.out.println("\nSolicitando URL: " + requestUrl); // Para depuración

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(requestUrl))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// System.out.println("Respuesta de la API:\n" + response.body()); // Puedes comentar esto una vez que confíes en que funciona

			ExchangeRateResponse apiResponse = gson.fromJson(response.body(), ExchangeRateResponse.class);

			if ("success".equals(apiResponse.getResult())) {
				double conversionRate = apiResponse.getConversion_rate();
				double convertedAmount = amountToConvert * conversionRate;

				System.out.println("\n--- Resultado de la Conversión ---");
				System.out.printf("%.2f %s son %.2f %s%n", amountToConvert, fromCurrency, convertedAmount, toCurrency);
				System.out.println("Tasa de conversión actual: 1 " + fromCurrency + " = " + conversionRate + " " + toCurrency);
				System.out.println("---------------------------------");
			} else {
				System.err.println("\nError en la respuesta de la API para " + fromCurrency + " a " + toCurrency + ": " + apiResponse.getResult());
				System.err.println("Por favor, verifique los códigos de moneda o intente más tarde.");
				// Si la API devuelve un mensaje de error más específico, puedes intentar extraerlo aquí
				// ejemplo: if (apiResponse.getErrorType() != null) System.err.println("Detalles: " + apiResponse.getErrorType());
			}

		} catch (IOException | InterruptedException e) {
			System.err.println("\nError al realizar la solicitud HTTP o leer la respuesta: " + e.getMessage());
			System.err.println("Asegúrese de tener conexión a internet.");
			e.printStackTrace();
		} catch (com.google.gson.JsonSyntaxException e) {
			System.err.println("\nError al procesar la respuesta de la API (JSON inválido).");
			e.printStackTrace();
		}
	}
}