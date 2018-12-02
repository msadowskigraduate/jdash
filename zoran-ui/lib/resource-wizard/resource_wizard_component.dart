import 'dart:async';

import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_components/material_button/material_button.dart';
import 'package:angular_components/model/action/async_action.dart';
import 'package:angular_components/utils/angular/scroll_host/angular_2.dart';
import 'package:sheets_dashboard/resource-wizard/steps/step_a_components.dart';
import 'package:sheets_dashboard/routing/routing.dart';
import 'package:sheets_dashboard/zoran_service.dart';

@Component(
  selector: 'new-resource',
  styleUrls: const ['resource_wizard_component.scss.css'],
  templateUrl: 'resource_wizard_component.html',
  directives: const [
    coreDirectives,
    MaterialStepperComponent,
    StepDirective,
    SummaryDirective,
    MaterialButtonComponent,
    StepAComponent
  ],
  providers: const [
    materialProviders,
  const ClassProvider(AppRoutes),
  scrollHostProviders
  ],
)
class ResourceWizardComponent {

  ProjectDetails details;

  bool showButton = false;

  void toggleContinue() {
    showButton = !showButton;
  }

  void validDelayedCheck(AsyncAction<bool> action) {

  }
}