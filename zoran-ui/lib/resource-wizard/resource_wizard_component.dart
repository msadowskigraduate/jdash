import 'dart:async';

import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_components/material_button/material_button.dart';
import 'package:angular_components/model/action/async_action.dart';
import 'package:angular_components/utils/angular/scroll_host/angular_2.dart';
import 'package:sheets_dashboard/resource-browser/resource-view/resource_view_component.dart';
import 'package:sheets_dashboard/resource-browser/resource_browser_component.dart';
import 'package:sheets_dashboard/resource-wizard/steps/step_a/step_a_components.dart';
import 'package:sheets_dashboard/resource-wizard/steps/step_b/step_b_component.dart';
import 'package:sheets_dashboard/routing/routing.dart';
import 'package:sheets_dashboard/user/user_service.dart';
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
    StepAComponent,
    StepBComponent,
    ResourceBrowserComponent,
    ResourceViewComponent,
  ],
  providers: const [
    materialProviders,
  const ClassProvider(AppRoutes),
  scrollHostProviders
  ],
)
class ResourceWizardComponent {
  final UserService userService;


  ResourceWizardComponent(this.userService);

  ProjectDetails _details;

  ProjectDetails get details {
    if(_details == null) {
      _details = ProjectDetails.empty(userService.user.login);
    }
    return _details;
  }

  bool showButton = false;

  void toggleContinue() {
    showButton = !showButton;
  }

  void validDelayedCheck(AsyncAction<bool> action) {
     if(details.name == null) {
       throw new Exception('Name cannot be null');
     }
  }
}