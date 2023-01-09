package main.groovy

print "The answer is "

def lines = readFileInList("../../../src/main/resources/data2021/dayOne")
def ints = lines.collect {it.toInteger()}

def count = 0

def runningTotal = ints.drop(ints.size() % 3).withIndex().collect{ints[it.v2] + ints[it.v2 + 1] + ints[it.v2 + 2] }

//for (index in 0 .. runningTotal.size() - 2) {
//    if (runningTotal[index] < runningTotal[index+1] ) {
//        ++count
//    }
//}

count = runningTotal.withIndex().count { it.v2 != runningTotal.size() -1 && runningTotal[it.v2] < runningTotal[it.v2 + 1] }

println (count)

List<String> readFileInList(String filePath) {
    File file = new File(filePath)
    def lines = file.readLines()
    return lines
}