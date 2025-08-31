import 'package:eq_inventory_mobile/Dashboard.dart';
import 'package:eq_inventory_mobile/loginForm.dart';
import 'package:eq_inventory_mobile/theme.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return  MaterialApp(
      theme: appTheme,
      home: const Scaffold(
        body: SingleChildScrollView(
            child: Dashboard()
        ),
      ),
    );
  }
}
