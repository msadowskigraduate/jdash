import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_components/focus/focus.dart';
import 'package:angular_components/material_button/material_button.dart';
import 'package:angular_components/material_icon/material_icon.dart';
import 'package:angular_components/material_input/material_input.dart';
import 'package:angular_components/model/ui/has_factory.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:zoran.io/services/resource_service.dart';
import 'package:zoran.io/services/zoran_service.dart';
import 'package:zoran.io/resource-wizard/steps/step_b'
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
  providers: const [
    materialProviders,
  ],
)
class StepBComponent implements OnInit {
  final NewResourceService _newResourceService;
  final ZoranService _zoranService;

  NewResourceService get newResourceService => _newResourceService;

  StepBComponent(this._newResourceService, this._zoranService);

  NewResourceModel model;
  List<String> languages;

  bool langaugeAndBuildChosen() {
    return _newResourceService.request != null &&
        _newResourceService.request.projectLanguage != null &&
        _newResourceService.request.type != null;
  }

  void onChange(String text) {
    _newResourceService.request.name = text;
  }

  String get singleSelectedBuildApp => _newResourceService.buildApp == null
      ? "Choose one" : _newResourceService.buildApp;

  String get singleSelectedLanguage {
    var projectLang = _newResourceService.request.projectLanguage;
    return projectLang != null ? projectLang : "Choose one ";
  }

  String get multiSelectedDependencies {
    if (_newResourceService.dependencies == null ||
        _newResourceService.dependencies == null) {
      return "Oops... Something went bad.";
    }
    var size = _newResourceService.dependencies.length;
    if (size == 0) {
      return "Empty :(";
    } else if (size == 1) {
      return _newResourceService.dependencies.first.name;
    } else {
      return _newResourceService.dependencies.first.name +
          " + ${size - 1} more";
    }
  }

  StringSelectionOptions<String> mavenOrGradle =
      StringSelectionOptions(["Maven", "Gradle", "Other"]);

  StringSelectionOptions<String> get LanguageOptions =>
      StringSelectionOptions(languages != null ? languages : []);

  StringSelectionOptions get dependenciesOptions => model == null
      ? new StringSelectionOptions([])
      : ExampleSelectionOptions(model.dependencies);

  FactoryRenderer eventRenderFactory = (_) => DependencyRendererNgFactory;

  @override
  void ngOnInit() async {
    model = await _zoranService.getNewResourceModel(null, null);
    languages = await _zoranService.getLanguages();
  }
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
