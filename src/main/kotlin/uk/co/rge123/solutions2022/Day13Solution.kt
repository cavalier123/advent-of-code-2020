package main.kotlin.uk.co.rge123.solutions2022
import main.kotlin.uk.co.rge123.utils.splitFileToLines

// [[],[[[5]],[[]],[],1,6],[0,8,10],[3,[],[0],2]]
// [[[2,[1],9,[4,2],6],[[1,0,8,4,10],[1,7,6],9,5],[3,2,4,5,[9,7,2]],[[6,4],5,[1],7],6],[10,[3,10,[0,4,2],5],10,[],0]]

fun main() {
    val filename = "day13"
    val solution = solveItDayThirteenPartOne2022(splitFileToLines(filename))
    print ("Solution is $solution")
    val solution2 = solveItDayThirteenPartTwo2022(splitFileToLines(filename))
    print ("Solution is $solution2")
}

fun solveItDayThirteenPartTwo2022(lines: List<String>): Int {

    val lists = mutableListOf<String>()

    for (line in lines) {
        if (!line.isEmpty()) {
           lists.add(line)
        }
    }

    lists.sortWith(ListComparator())

    for (list in lists) {
        println(list)
    }

    val index1 = lists.indexOf("[[2]]") + 1
    val index2 = lists.indexOf("[[6]]") + 1

    return index1 * index2

}


fun solveItDayThirteenPartOne2022(lines: List<String>): Int {

    var lists = Array(2) { ""}
    var listIndex = 0

    var sumIndexes = 0
    var curIndex = 1

    for (line in lines) {
        if (line.isEmpty()) {
            val compVal = compareLists(lists[0], lists[1])
            println ("comp val = $compVal")
            if (compVal == -1) {
                sumIndexes += curIndex
            }
            ++curIndex
        } else {
            lists[listIndex] = line
            listIndex = (listIndex + 1) %2
        }
    }
    val compVal = compareLists(lists[0], lists[1])
    println ("comp val = $compVal")
    if (compVal == -1) {
        sumIndexes += curIndex
    }

    return sumIndexes
}

fun areEqual(leftString: String, rightString: String): Int {
    println ("Comparing $leftString, $rightString")

    val leftList = leftString.substring(1, leftString.length - 1)
    var rightList = rightString.substring(1, rightString.length - 1)

    return compareLists(leftList, rightList)

    return 0
}

//.sortedWith <Person> (object : Comparator <Person> {
//    override fun compare (p0: Person, pi: Person) : Int {

class ListComparator : Comparator<String> {
    override fun compare (l1: String, l2: String) : Int {
        return compareLists(l1, l2)
    }

}



fun compareLists(leftString: String, rightString: String): Int {

    println ("Comparing $leftString------$rightString")

    // comma case
    if (leftString.startsWith(",") && rightString.startsWith(",")) {
        return compareLists(leftString.substring(1), rightString.substring(1))
    }
    // end of two list case
    if (leftString.startsWith("]") && rightString.startsWith("]")) {
        if (leftString.length == 1 && rightString.length > 1) {
            return -1
        } else if (leftString.length > 1 && rightString.length == 1) {
            return 1
        } else {
            return compareLists(leftString.substring(1), rightString.substring(1))
        }
    }

    // end of one list case
    if (leftString.startsWith("]") && !rightString.startsWith("]")) {
        return -1
    } else if (!leftString.startsWith("]") && rightString.startsWith("]")) {
        return 1
    }

    // other cases - two opening brackets
    if (leftString. startsWith("[") && rightString.startsWith("[")) {
        val leftList = leftString.substring(1)
        var rightList = rightString.substring(1)
        return compareLists(leftList, rightList)
    }

    if (leftString.startsWith("[") && !rightString.startsWith("[")) {
        val leftList = leftString.substring(1)
        var rightList = ""
        if (rightString[0].isDigit() && (rightString.length == 1 || !rightString[1].isDigit())) {
            rightList = rightString[0] + "]" + rightString.substring(1)
        } else {
            rightList = "10]" + rightString.substring(2)
        }
        return compareLists(leftList, rightList)
    }

    if (!leftString.startsWith("[") && rightString.startsWith("[")) {
        val rightList = rightString.substring(1)
        var leftList = ""
        if (leftString[0].isDigit() && (leftString.length == 1 || !leftString[1].isDigit())) {
            leftList = leftString[0] + "]" + leftString.substring(1)
        } else {
            leftList = "10]" + leftString.substring(2)
        }
        return compareLists(leftList, rightList)
    }

    // both digits case
    if (!leftString.startsWith("[") && !rightString.startsWith("[")) {
        var leftVal = 0
        var rightVal = 0
        if (leftString[0].isDigit() && (leftString.length == 1 || !leftString[1].isDigit())) {
            leftVal = leftString[0].digitToInt()
        } else {
            leftVal = 10
        }
        if (rightString[0].isDigit() && (rightString.length == 1 || !rightString[1].isDigit())) {
            rightVal = rightString[0].digitToInt()
        } else {
            rightVal = 10
        }
        val ret = compareInts(leftVal, rightVal)
        if (ret == -1) return -1
        if (ret == 1) return 1
        return compareLists(leftString.substring(if (leftVal == 10) 2 else 1),
            rightString.substring(if (rightVal == 10) 2 else 1))
    }

    return 99
}

fun compareInts(leftNum: Int, rightNum: Int) : Int {
    if (leftNum < rightNum) return -1
    if (leftNum == rightNum) return 0
    return 1
}



