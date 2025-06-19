package com.example.assignment_writer.Login;

public class isChecked {

    public boolean validPassword(String password){
        int count=0;
        if(password.length()<6){
            return false;
        }
        else{
            for(int i=0;i<password.length();i++){

                if(password.charAt(i)=='!'||password.charAt(i)=='@'||password.charAt(i)=='#'||
                        password.charAt(i)=='$'|| password.charAt(i)=='%'|| password.charAt(i)=='^'||
                        password.charAt(i)=='&'||password.charAt(i)=='*'||password.charAt(i)=='+'||
                        password.charAt(i)=='='||password.charAt(i)=='?'||password.charAt(i)=='-'
                )
                {
                    count++;
                    break;
                }
            }
            if(count==1){
                return true;
            }
            else{
                return false;
            }
        }
    }
    public boolean validEmail(String email) {
        String matchString = "@gmail.com";

        if (email.length() < matchString.length()) {
            return false;
        }
        String lastPart = email.substring(email.length() - matchString.length());
        return matchString.equals(lastPart);
    }

}
