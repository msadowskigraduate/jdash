import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:angular_components/focus/focus.dart';
import 'package:angular_components/material_button/material_button.dart';
import 'package:angular_components/material_icon/material_icon.dart';
import 'package:angular_components/material_input/material_input.dart';
import 'package:sheets_dashboard/services/markdown-viewer/markdown_viewer_component.dart';
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

  @Input()
  ProjectDetails details;

  @ViewChild(MarkdownViewerComponent)
  MarkdownViewerComponent viewer;

  List<String> licences;
  bool btEnabled = false;
  bool initCompleted = false;

  StepAComponent(this._zoranService);

  bool firstCompleted() {
    return details != null && details.projectName != null && details.name != null;
  }

  void onChange(String text) {
    details.name = text;
  }

  @override
  void ngAfterViewInit() async {
    licences = await _zoranService.getLicences();
  }

  void parseMarkdown() {
    if(viewer != null && details.description != null) {
      viewer.renderMarkdown();
    }
  }

  void changedState() {
    btEnabled ? details.visibility= ResourceVisibility.PUBLIC
        : details.visibility=ResourceVisibility.PUBLIC;
    btEnabled = !btEnabled;
  }

  void create(String type) {
    NewResourceType nrType = NewResourceType.values
        .firstWhere((t) => t.toString() == type);
    details.type = nrType;
    initCompleted = true;
  }
}