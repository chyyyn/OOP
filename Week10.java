import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Week10 {
    public List<String> getAllFunctions(String fileContent, String packageName) {
        List<String> result = new ArrayList<>();

        String regex = "static\\s+\\w+(\\[\\])?\\s+(\\w+\\s*\\(([^\\)]*)\\))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileContent);

        while (matcher.find()) {
            String signature = matcher.group(2);
            signature = signature.replace("static", "").trim();

            String[] parts = signature.split("\\(");
            String methodName = parts[0];
            String parameters = parts[1];
            parameters = parameters.replace(")", "");

            String[] params = parameters.split(",");
            List<String> paramTypes = new ArrayList<>();
            boolean hasPackageParams = false;

            for (String param : params) {
                if (!param.isEmpty()) {
                    String[] typeAndName = param.trim().split("\\s+");
                    String type = typeAndName[0];

                    if (Character.isUpperCase(type.charAt(0))) {
                        String packageNameFromClass = getPackageNameFromClass(type, fileContent);
                        if (packageNameFromClass.startsWith(packageName)) {
                            hasPackageParams = true;
                        }
                    }

                    paramTypes.add(type);
                }
            }

            if (hasPackageParams) {
                signature = methodName + "(" + String.join(", ", paramTypes) + ")";
                result.add(signature);
            }
        }

        return result;
    }

    private String getPackageNameFromClass(String className, String fileContent) {
        String regex = "import\\s+(.*\\." + className + ");";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileContent);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }
}
