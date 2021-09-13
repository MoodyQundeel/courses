public class Part1 {
    public void findAbc(String input) {
    int index = input.indexOf("abc");
    while (true) {
        if (index == -1) {
            break;
        }
        if (index >= input.length() - 3) {
            break;
        }
        System.out.println(index);
        String found = input.substring(index+1, index+4);
        System.out.println(found);
        index = input.indexOf("abc", index+3);
        System.out.println("index after updating: " + index);
    }
}
   public void test() {
    // findAbc("abcd");
    findAbc("abcdabc");
}
}
