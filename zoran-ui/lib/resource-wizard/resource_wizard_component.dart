import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_components/material_button/material_button.dart';
import 'package:angular_components/utils/angular/scroll_host/angular_2.dart';
import 'package:angular_components/utils/browser/dom_service/angular_2.dart';
import 'package:angular_components/utils/browser/window/module.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:zoran.io/resource-browser/resource-view/resource_view_component.dart';
import 'package:zoran.io/resource-browser/resource_browser_component.dart';
import 'package:zoran.io/resource-wizard/steps/step_a/step_a_components.dart';
import 'package:zoran.io/resource-wizard/steps/step_b/step_b_component.dart';
import 'package:zoran.io/routing/routing.dart';
import 'package:zoran.io/services/resource_service.dart';

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
    FixedMaterialTabStripComponent,
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
  providers: const [
    materialProviders,
    const ClassProvider(AppRoutes),
    scrollHostProviders,
    domServiceBinding,
    rtlProvider,
    windowBindings,
  ],
)
class ResourceWizardComponent implements OnInit {
  final NewResourceService newResourceService;

  @Input('validated')
  bool StepAValidated = true;
  bool showButton = false;
  int tabIndex = 0;
  String ymlConfig;

  ResourceWizardComponent(this.newResourceService);

  final tabLabels = const <String>[
    'Basic',
    'Advanced Editor'
  ];

  void fromYML() {
    if(ymlConfig != null) {
      newResourceService.createNewRequestFromYML(ymlConfig);
    }
  }

  void onTabChange(TabChangeEvent event) {
    tabIndex = event.newIndex;
  }

  void toggleContinue() {
    showButton = !showButton;
  }

  @override
  void ngOnInit() {
    if(newResourceService.request == null) {
      newResourceService.createNewRequest();
    }
  }
}