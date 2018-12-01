import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:angular_components/focus/focus.dart';
import 'package:angular_components/material_button/material_button.dart';
import 'package:angular_components/material_icon/material_icon.dart';
import 'package:angular_components/material_input/material_input.dart';

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
  List<StepModel> modelList = [
    new StepModel("Project Title", "Enter your Project's name:", 25),
    new StepModel("Group Id", "Project's group id:", 25),
    new StepModel("Artifact Id", "Project's artifact id:", 25)
  ];

  void test() {
    print("test!");
  }
}

class StepModel {
  String _label;
  String _inputLabel;
  int _maxCount;

  StepModel(this._label, this._inputLabel, this._maxCount);

  String get label => _label;
  String get inputLabel => _inputLabel;
  int get maxCount => _maxCount;
}