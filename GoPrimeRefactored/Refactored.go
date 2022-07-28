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

	IsNumberPrimee(readFile())
}

func IsNumberPrimee(value []string) {
	var sayac int = 0

	for i := 0; i < len(value); i++ {
		sayac = 0
		deger, err := strconv.Atoi(value[i]) // string degeri integer degerine donusturur
		if err != nil {
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
			fmt.Println(deger, "=>NotPrime")
		}

	}
}

func readFile() []string {
	readFile, err := os.Open("numbers.txt")
	if err != nil {
		log.Fatal(err)
	}

	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanLines)
	var fileLines []string
	//fileLines := []string{}
	for fileScanner.Scan() {
		fileLines = append(fileLines, fileScanner.Text())
	}

	readFile.Close()
	return fileLines
}
