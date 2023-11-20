import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Week10 class has the function of getting all the signatures of the static methods in a file.
 */
public class Week10 {

    /**
     * The method getAllFunctions gets all the signatures of the static methods in a file.
     * @param fileContent The content of the file to be processed.
     * @return A list of strings, each string is a signature of a static method found in the file.
     */
    public List<String> getAllFunctions(String fileContent) {
        // Initialize the result list
        List<String> result = new ArrayList<>();

        // Initialize the regular expression to search for the signature of the static method
        // The regular expression has the form: static <return type> <method name>(<parameter list>)
        String regex = "static\\s+\\w+(\\[\\])?\\s+(\\w+\\s*\\(([^\\)]*)\\))";

        // Create a Pattern object from the regular expression
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object to match the regular expression with the file content
        Matcher matcher = pattern.matcher(fileContent);

        // Loop through all the matching results
        while (matcher.find()) {
            String signature = matcher.group(2);

            // Remove the static keyword and extra spaces
            signature = signature.replace("static", "").trim();

            // Split the signature of the method into the method name and the parameter list
            String[] parts = signature.split("\\(");
            String methodName = parts[0];
            String parameters = parts[1];

            // Remove the last parenthesis
            parameters = parameters.replace(")", "");

            // Split the parameter list into individual parameters
            String[] params = parameters.split(",");

            // Initialize a list to store the parameter types
            List<String> paramTypes = new ArrayList<>();

            // Loop through all the parameters
            for (String param : params) {
                // If the parameter is not empty
                if (!param.isEmpty()) {
                    // Split the parameter into type and name
                    String[] typeAndName = param.trim().split("\\s+");

                    // Get the parameter type
                    String type = typeAndName[0];

                    // If the parameter type is a class, then add the package name before it
                    if (Character.isUpperCase(type.charAt(0))) {
                        type = getPackageName(fileContent, type) + "." + type;
                    }

                    // Add the parameter type to the list
                    paramTypes.add(type);
                }
            }

            // Concatenate the method name and the parameter type list into the method signature
            signature = methodName + "(" + String.join(",", paramTypes) + ")";

            // Add the method signature to the result list
            result.add(signature);
        }

        // Return the result list
        return result;
    }

    /**
     * The method getPackageName gets the package name of a class in a file.
     * @param fileContent The content of the file to be processed.
     * @param className The name of the class to get the package name.
     * @return A string that is the package name of the class.
     */
    private String getPackageName(String fileContent, String className) {
        // Initialize the default package name as java.lang
        String packageName = "java.lang";

        // If the class name is Object, return java.lang
        if (className.equals("Object")) {
            return packageName;
        }

        // Initialize the regular expression to search for the package declaration
        String regex = "package\\s+(\\w+(\\.\\w+)*)\\s*;";

        // Create a Pattern object from the regular expression
        Pattern pattern = Pattern.compile(regex);

        // Create a Matcher object to match the regular expression with the file content
        Matcher matcher = pattern.matcher(fileContent);

        // If the package declaration is found
        if (matcher.find()) {
            // Get the package name from the matching result
            packageName = matcher.group(1);
        }

        // Return the package name
        return packageName;
    }
}