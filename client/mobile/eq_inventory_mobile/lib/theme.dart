import 'package:flutter/material.dart';

class AppColors{
  static const Color appDarkBlue = Color(0xFF041C32);
  static const Color appBlue = Color(0xFF04293A);
  static const Color appLightBlue = Color(0xFF064663);
  static const Color accentColor = Color(0xFFECB365);
  static const Color appWhite = Color(0xFFD7D7D7);
  static const Color borderColor = Color(0xFFB8B8B8);
  static const Color textSecondary = Color(0xFF777777);
}


final ThemeData appTheme = ThemeData(
  colorScheme: const ColorScheme(
    brightness: Brightness.light,
    primary: AppColors.appLightBlue,
    secondary: AppColors.appLightBlue,
    tertiary: AppColors.accentColor,
    surface: AppColors.appLightBlue,
    error: Colors.red,
    onPrimary: AppColors.appWhite,
    onSecondary: AppColors.appWhite,
    onTertiary: AppColors.appWhite,
    onSurface: AppColors.appWhite,
    onError: AppColors.appWhite,
  )
);