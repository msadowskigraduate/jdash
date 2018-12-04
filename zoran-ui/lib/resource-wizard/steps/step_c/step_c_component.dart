import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_components/utils/browser/dom_service/angular_2.dart';
import 'package:angular_components/utils/browser/window/module.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:angular_components/focus/focus.dart';
import 'package:angular_components/material_button/material_button.dart';
import 'package:angular_components/material_icon/material_icon.dart';
import 'package:angular_components/material_input/material_input.dart';
import 'package:sheets_dashboard/zoran_service.dart';

@Component(
  selector: 'step-c',
  styleUrls: const ['step_c_component.scss.css'],
  templateUrl: 'step_c_component.html',
  directives: const [
    coreDirectives,
    MaterialInputComponent,
    formDirectives,
    AutoFocusDirective,
    MaterialButtonComponent,
    MaterialIconComponent,
    materialInputDirectives,
    MaterialMultilineInputComponent,
    materialNumberInputDirectives,
    MaterialPaperTooltipComponent,
    MaterialTooltipTargetDirective,
    FixedMaterialTabStripComponent
  ],
  providers: const
  [
    materialProviders,
    domServiceBinding,
    rtlProvider,
    windowBindings,
  ],
)
class StepCComponent {

  @Input()
  ProjectDetails details;

  int tabIndex = 0;

  final tabLabels = const <String>[
    'Basic',
    'Advanced Editor'
  ];

  bool firstCompleted() {
    return details != null && details.projectName != null && details.name != null;
  }

  bool secondCompleted() {
    return details.artifactId != null && details.groupId != null;
  }

  void onTabChange(TabChangeEvent event) {
    tabIndex = event.newIndex;
  }

  void onChange(String text) {
    details.name = text;
  }
}