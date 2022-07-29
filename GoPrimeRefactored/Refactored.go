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
	var filename string = "numbers.txt"
	var FileDetails []string = readFile(filename)
	WriteFileIsPrime(FileDetails)
}

func WriteFileIsPrime(read []string) {
	WriteFile, err := os.Create("results.txt")
	Error(err)
	defer WriteFile.Close()

	for i := 0; i < len(read); i++ {
		value, err := strconv.Atoi(read[i])
		Error(err)
		FileWriter := fmt.Sprintf("%d%s", value, IsPrime(value))
		WriteFile.WriteString(FileWriter + "\n")
	}
}

func Error(err error) {
	if err != nil {
		panic(err)
	}
}

func IsPrime(data int) string {
	var counter int = 0
	for i := 2; i <= int(math.Floor(float64(data)/2)); i++ {
		if data%i == 0 {
			counter++
		}
	}
	if counter == 0 {
		PrintPrime(counter, data)
		return "=>Prime"
	} else {
		PrintPrime(counter, data)
		return "=>NotPrime"
	}
}

func PrintPrime(counter int, value int) {
	if counter == 0 {
		fmt.Println(value, "=>Prime")
	} else {
		fmt.Println(value, "=>NotPrime")
	}
}

func readFile(filename string) []string {
	readFile, err := os.Open(filename)
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
