package main.kotlin.uk.co.rge123.solutions


fun main() {
    val solution = solveIt("aeiou")
    print ("Solution is $solution")
}


val masterHash = mutableMapOf<Pair<ArrayDeque<Int>, ArrayDeque<Int>>, Boolean>()

fun solveIt(input: String): Int {

    val playerOneHand = ArrayDeque<Int>()
    //playerOneHand.addAll(listOf(9, 2, 6, 3, 1))
    playerOneHand.addAll(listOf(
            47,
            19,
            22,
            31,
            24,
            6,
            10,
            5,
            1,
            48,
            46,
            27,
            8,
            45,
            16,
            28,
            33,
            41,
            42,
            36,
            50,
            39,
            30,
            11,
            17))

    val playerTwoHand = ArrayDeque<Int>()
    //playerTwoHand.addAll(listOf(5, 8, 4, 7, 10))
    playerTwoHand.addAll(listOf(
            4,
            18,
            21,
            37,
            34,
            15,
            35,
            38,
            20,
            23,
            9,
            25,
            32,
            13,
            26,
            2,
            12,
            44,
            14,
            49,
            3,
            40,
            7,
            43,
            29))

    val playerOneWon = playGame(playerOneHand, playerTwoHand)

    var ret = 0

    if (playerOneWon) {
        val handSize = playerOneHand.size
        ret = playerOneHand.mapIndexed{ i, t -> (handSize - i) * t}.sum()
    } else {
        val handSize = playerTwoHand.size
        ret = playerTwoHand.mapIndexed{ i, t -> (handSize - i) * t}.sum()
    }

    return ret

}

fun playGame(playerOneHand: ArrayDeque<Int>, playerTwoHand: ArrayDeque<Int>): Boolean {

    //println ("play game called")

    val storedResult = masterHash[Pair(playerOneHand, playerTwoHand)]
    if (storedResult != null) {
        //println("RETRIEVING STORED RESULT")
        return storedResult
    }

    val playOneHandInitial = ArrayDeque(playerOneHand)
    val playTwoHandInitial = ArrayDeque(playerTwoHand)

    val prevOne = mutableListOf<ArrayDeque<Int>>()
    val prevTwo = mutableListOf<ArrayDeque<Int>>()

    do {

//        println ("play hand called")
//        println(playerOneHand)
//        println(playerTwoHand)
        for (index in prevOne.indices) {
            if(handsEqual(prevOne[index], playerOneHand) && handsEqual(prevTwo[index], playerTwoHand)) {
                //println("Hands are equal")
                return true // player one has won
            }
        }

        prevOne.add(ArrayDeque(playerOneHand))
        prevTwo.add(ArrayDeque(playerTwoHand))

        playHand(playerOneHand, playerTwoHand)


    } while (playerOneHand.size > 0 && playerTwoHand.size > 0)

    masterHash[Pair(playOneHandInitial, playTwoHandInitial)] = playerOneHand.size > 0
    //println("Master hash size = ${masterHash.size}")

    return(playerOneHand.size > 0)
}

fun handsEqual(playerOneHand: ArrayDeque<Int>, playerTwoHand: ArrayDeque<Int>): Boolean {
    if (playerOneHand.size != playerTwoHand.size) return false
    for (index in playerOneHand.indices) {
        if (playerOneHand[index] != playerTwoHand[index]) return false
    }
//    println("equal")
//    println(playerOneHand)
//    println(playerTwoHand)
    return true
}


fun playHand(handOne: ArrayDeque<Int>, handTwo: ArrayDeque<Int>) {

    val cardOne = handOne.removeFirst()
    //println(cardOne)
    val cardTwo = handTwo.removeFirst()
    //println(cardTwo)

    if (handOne.size >= cardOne && handTwo.size >= cardTwo) { // recursive game
        //println ("recursing")
        val handOneCopy = ArrayDeque<Int>()
        val handTwoCopy = ArrayDeque<Int>()
        //println ("orginal = $handOne}")
        for (i in 0 until cardOne) {
            handOneCopy.add(handOne[i])
        }
        //println ("new = $handOneCopy}")
        for (i in 0 until cardTwo) {
            handTwoCopy.add(handTwo[i])
        }

        val playOneWon = playGame(handOneCopy, handTwoCopy)
        //println ("EXITING recursing")

        if (playOneWon) {
            handOne.addLast(cardOne)
            handOne.addLast(cardTwo)
        } else {
            handTwo.addLast(cardTwo)
            handTwo.addLast(cardOne)
        }
    } else { // normal game
        //println ("ordinary")

        if (cardOne > cardTwo) {
            handOne.addLast(cardOne)
            handOne.addLast(cardTwo)
        } else {
            handTwo.addLast(cardTwo)
            handTwo.addLast(cardOne)
        }
    }

}