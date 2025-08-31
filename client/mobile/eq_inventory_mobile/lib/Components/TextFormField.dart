import 'package:eq_inventory_mobile/theme.dart';
import 'package:flutter/material.dart';

class AppInputField extends StatefulWidget {
  final String? label;
  final TextEditingController controller;
  final bool obscureText;
  final Function(String?) validator;
  final Function(bool?) onChanged;
  final Widget icon;

  const AppInputField({
    super.key,
    this.label,
    required this.controller,
    required this.obscureText,
    required this.validator,
    required this.icon,
    required this.onChanged}
  );


  @override
  State<AppInputField> createState() => _AppInputFieldState();
}

class _AppInputFieldState extends State<AppInputField> {
  @override
  Widget build(BuildContext context) {
    return TextFormField(
      obscureText: widget.obscureText,
      validator: (val)=> widget.validator(val),
      onChanged: (val)=> widget.onChanged,
      controller: widget.controller,
      style: const TextStyle(
        color: AppColors.appWhite
      ),
      decoration:  InputDecoration(
        prefixIcon: widget.icon,
        prefixIconColor: AppColors.textSecondary,
        labelText: widget.label,
          labelStyle: TextStyle(color: AppColors.textSecondary),
          border: OutlineInputBorder(
          borderSide: BorderSide(color: AppColors.borderColor, width: 1.0),
      ),

        // focused style
        focusedBorder: OutlineInputBorder(
          borderSide: BorderSide(
            color: AppColors.appLightBlue,
            width: 3.0
          )
        ),


        // error style
        errorBorder: OutlineInputBorder(
          borderSide: BorderSide(
            color: Colors.red,
            width: 3.0
          )
        )
      ),

    );
  }
}

