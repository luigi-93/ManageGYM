package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//contiene tutte le configurazioni necessarie per il progetto
@SpringBootConfiguration
//è un annotazione che gestisce la ricerca automatica le configurazioni in giro per la classe e avviarle.
@EnableAutoConfiguration
//in fase di avvio cerca in tutti i package classi con un precisa annotazione,
@SpringBootApplication
@ComponentScan


public class DemoApplication {

	public static void main(String[] args) {

		//il metodo run è un entrypoint, all'interno el primo parametro gli dico il percorso dove trova le info necessarie per partire (dove trova il nostro entrypoint), args
		SpringApplication.run(DemoApplication.class, args);
	}

}
