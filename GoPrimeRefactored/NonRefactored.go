package main

import (
	"bufio"
	"fmt"
	"log"
	"math"
	"os"
	"strconv"
)

func main() {

	f, err := os.Open("numbers.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer f.Close()
	scanner := bufio.NewScanner(f)

	for scanner.Scan() {
		var text string = scanner.Text() // text değişkeni sayılar.txt dosyasındaki her satırın değerini alır
		deger, err := strconv.Atoi(text) // string degeri integer degerine donusturur
		var sayac int = 0

		if err != nil { // hata varsa
			panic(err)
		}

		for i := 2; i <= int(math.Floor(float64(deger)/2)); i++ {
			if deger%i == 0 {
				sayac++
			}
		}
		if sayac == 0 {
			fmt.Println(deger, "=>Prime")
		} else {
			fmt.Println(deger, "NotPrime")
		}

	}

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}
