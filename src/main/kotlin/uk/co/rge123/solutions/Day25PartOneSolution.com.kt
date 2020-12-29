package main.kotlin.uk.co.rge123.solutions

// card public key 10604480
// door public key 4126658

fun main() {
    val solution = solveItDay25PartOne("aeiou")
    print ("Solution is $solution")
}

fun solveItDay25PartOne(input: String): Int {

    val cardPublicKLey = 10604480
    val doorPublicKey = 4126658

    val cardLoopSize = findLoopSizeGivenKey(7, cardPublicKLey)
    println("done card loop = $cardLoopSize")
    val enc = encrypt(7, cardLoopSize)
    println("enc = $enc")

    val doorLoopSize = findLoopSizeGivenKey(7, doorPublicKey)
    println("done door loop = $doorLoopSize")
    val enc2 = encrypt(7, doorLoopSize)
    println("enc2 = $enc2")

    val cardEncryptKey = encrypt(doorPublicKey, cardLoopSize)
    val doorEncryptKey = encrypt(cardPublicKLey, doorLoopSize)

    println("Card encrypt key = $cardEncryptKey")
    println("Door encrypt key = $doorEncryptKey")

    return 0
}


fun findLoopSizeGivenKey(subject: Int, key: Int): Int {

    var value = 1
    for (loopSize in 1 .. 20201227) {
        value = (value * subject) % 20201227
        if (value == key) {
            return loopSize
        }
    }
    return 0
}

fun encrypt(subject: Int, loopSize: Int): Long {
    var value = 1L
    for (i in 1 .. loopSize) {
        value = (value * subject) % 20201227
    }
    return value
}




