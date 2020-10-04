package com.tinydoc.utils;

public class MailTemplates {

    public static String envioClave(String usuario, String pswd) {
        return String.format("Hola %s! <br> Se ha creado tu cuenta en el portal TinyDoc, " +
                        "podrás ingresar con tu correo y la siguiente contraseña:" +
                        "<br><br> <b>%s</b> <br><br> Saludos_!",
                String.valueOf(usuario.charAt(0)).toUpperCase() + usuario.substring(1), pswd);
    }
}
