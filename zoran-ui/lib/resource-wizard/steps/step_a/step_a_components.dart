import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:angular_components/focus/focus.dart';
import 'package:angular_components/material_button/material_button.dart';
import 'package:angular_components/material_icon/material_icon.dart';
import 'package:angular_components/material_input/material_input.dart';
import 'package:zoran.io/services/markdown-viewer/markdown_viewer_component.dart';
import 'package:zoran.io/services/resource_service.dart';
import 'package:zoran.io/services/zoran_service.dart';

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
    MarkdownViewerComponent,
    MaterialToggleComponent,
    NgClass,
    NgFor
  ],
  providers: const
  [
    materialProviders
  ],
)
class StepAComponent implements AfterViewInit {

  final ZoranService _zoranService;
  final NewResourceService _newResourceService;

  @Input("validated")
  bool isValidated;

  @ViewChild(MarkdownViewerComponent)
  MarkdownViewerComponent viewer;

  List<String> licences;
  bool btEnabled = false;

  StepAComponent(this._zoranService, this._newResourceService);

  NewResourceService get newResourceService => _newResourceService;

  @override
  void ngAfterViewInit() async {
    licences = await _zoranService.getLicences();
  }

  void onChange(String text) {
    _newResourceService.request.name = text;
  }


  void parseMarkdown() {
    if(viewer != null && _newResourceService.request.description != null) {
      viewer.renderMarkdown();
    }
  }

  void changedState() {
    btEnabled ?
    _newResourceService.request.resourceVisibility = ResourceVisibility.PUBLIC :
    _newResourceService.request.resourceVisibility = ResourceVisibility.PRIVATE;
    btEnabled = !btEnabled;
  }
}