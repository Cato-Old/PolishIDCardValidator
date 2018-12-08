import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class PolishIDCardValidator implements Validator{
    @Override
    public boolean validate(String idNumber) {
        if (!hasValidFormat(idNumber)){
            return false;
        }
        int[] letters = lettersToInts(idNumber.substring(0,3));
        int[] ciphers = Arrays
                .stream(idNumber.substring(3).split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] allChar = IntStream.concat(Arrays.stream(letters),Arrays.stream(ciphers))
                .toArray();
        int[] weights = {7, 3, 1, 9, 7, 3, 1, 7, 3};
        int result = 0;
        for (int i = 0; i < 9; i++){
            result += allChar[i] * weights[i];
        }
        return result % 10 == 0;
    }

    private boolean hasValidFormat(String idNumber){
        return idNumber.matches("\\w{3}\\d{6}");
    }

    private int[] lettersToInts(String letters){
        Map<Character,Integer> lettersMap = new HashMap<>();
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int[] ints = IntStream.rangeClosed(10,35).toArray();
        lettersMap = IntStream.rangeClosed(0,25).boxed()
                .collect(Collectors.toMap(i -> alphabet[i],i->ints[i]));
        return Arrays.stream(letters.split(""))
                .map(x->x.charAt(0))
                .map(lettersMap::get)
                .mapToInt(x->x)
                .toArray();
    }
}
