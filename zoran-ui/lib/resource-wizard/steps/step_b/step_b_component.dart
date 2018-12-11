import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_components/focus/focus.dart';
import 'package:angular_components/material_button/material_button.dart';
import 'package:angular_components/material_icon/material_icon.dart';
import 'package:angular_components/material_input/material_input.dart';
import 'package:angular_components/model/ui/has_factory.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:sheets_dashboard/zoran_service.dart';
import 'package:sheets_dashboard/resource-wizard/steps/step_b'
    '/dependency_renderer_component.template.dart';

@Component(
  selector: 'step-b',
  styleUrls: const ['step_b_component.scss.css'],
  templateUrl: 'step_b_component.html',
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
    MaterialCheckboxComponent,
    MaterialDropdownSelectComponent,
    MaterialSelectSearchboxComponent,
    DropdownSelectValueAccessor,
    MultiDropdownSelectValueAccessor,
    DropdownButtonComponent,
  ],
  providers: const
  [
    materialProviders,
  ],
)
class StepBComponent {

  @Input()
  ProjectDetails details;
  final ZoranService zoranService;

  StepBComponent(this.zoranService) {
    model = zoranService.getNewResourceModel(null, null);
  }

  NewResourceModel model;

  bool firstCompleted() {
    return details != null && details.projectName != null &&
        details.name != null;
  }

  bool secondCompleted() {
    return details.artifactId != null && details.groupId != null;
  }

  void onChange(String text) {
    details.name = text;
  }

  String get singleSelectedBuildApp => details.buildApp == null ? " Select One" : details.buildApp;
  String get singleSelectedLanguage => details.projectLanguage == null ? " Select One" : details.projectLanguage;
  String get multiSelectedLanguage {
    if(details == null || details.dependencies == null) {
      return "Oops... Something went bad.";
    }
    var size = details.dependencies.length;
    if (size == 0) {
      return "Empty :(";
    } else if (size == 1) {
      return details.dependencies.first.name;
    } else {
      return details.dependencies.first.name + " + ${size - 1} more";
    }
  }

  StringSelectionOptions<String> buildAppOptions = StringSelectionOptions(
      ["Maven", "Gradle"]);

  StringSelectionOptions<String> get LanguageOptions =>
      StringSelectionOptions(zoranService.getLanguages());

  StringSelectionOptions get dependenciesOptions => ExampleSelectionOptions
    (model.dependencies);

  FactoryRenderer eventRenderFactory = (_) => DependencyRendererNgFactory;
}

class ExampleSelectionOptions<T> extends StringSelectionOptions<T>
    implements Selectable {
  ExampleSelectionOptions(List<T> options)
      : super(options, toFilterableString: (T option) => option.toString());
  ExampleSelectionOptions.withOptionGroups(List<OptionGroup> optionGroups)
      : super.withOptionGroups(optionGroups,
      toFilterableString: (T option) => option.toString());
  @override
  SelectableOption getSelectable(item) => SelectableOption.Selectable;
}