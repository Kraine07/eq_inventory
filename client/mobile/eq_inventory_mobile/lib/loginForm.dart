import 'package:eq_inventory_mobile/Components/TextFormField.dart';
import 'package:eq_inventory_mobile/theme.dart';
import 'package:flutter/material.dart';

class LoginForm extends StatefulWidget {
  const LoginForm({super.key});

  @override
  State<LoginForm> createState() => _LoginFormState();
}

class _LoginFormState extends State<LoginForm> {

  final _formKey = GlobalKey<FormFieldState>();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();

   onChanged(bool? value) {
    _formKey.currentState!.validate();
    return null;
   }

  String? emailValidator(value) {
    if (value!.isEmpty) {
      return "Email address is required.";
    } else if (value.length < 5) {
      return "Minimum 5 characters required.";
    }
    return null;
  }


  String? passwordValidator(value) {
    if (value.length < 8) {
      return "Minimum 8 characters required.";
    }
    return null;
  }

  @override
  Widget build(BuildContext context) {
    return  Padding(
      padding: EdgeInsets.all(20.0),
      child: Form(
        key: _formKey,
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [

            // Email input
            AppInputField(
              icon: const Icon(Icons.mail_outline),
              label: "Email",
                controller: _emailController,
                obscureText: false,
                validator: emailValidator,
                onChanged: onChanged),

            const SizedBox(height: 20.0,),

            // Password input
            AppInputField(
              icon: const Icon(Icons.lock_outline),
              label: "Password",
                controller: _passwordController,
                obscureText: true,
                validator: passwordValidator,
                onChanged: onChanged),

            SizedBox(height: 20.0,),

            //Button
            SizedBox(
              width: double.infinity,
              child: OutlinedButton(
                  onPressed: (){_formKey.currentState!.validate();},
                style: OutlinedButton.styleFrom(
                  padding: EdgeInsets.symmetric( vertical: 20),
                  backgroundColor: AppColors.appDarkBlue,
                  foregroundColor: AppColors.appWhite,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8.0)
                  ),
                  side: BorderSide(
                    color: AppColors.accentColor,
                    width: 1.0
                  )
                ),
                child: Text("Sign in"),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
