import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:angular_components/focus/focus.dart';
import 'package:angular_components/material_button/material_button.dart';
import 'package:angular_components/material_icon/material_icon.dart';
import 'package:angular_components/material_input/material_input.dart';
import 'package:sheets_dashboard/zoran_service.dart';

@Component(
  selector: 'step-a',
  styleUrls: const ['step_a_component.scss.css'],
  templateUrl: 'step_a_component.html',
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
  ],
  providers: const
  [
    materialProviders
  ],
)
class StepAComponent {

  @Input()
  ProjectDetails details;

  bool firstCompleted() {
    return details != null && details.projectName != null && details.name != null;
  }
}