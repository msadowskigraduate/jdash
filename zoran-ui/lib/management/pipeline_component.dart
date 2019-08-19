import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_components/material_button/material_button.dart';
import 'package:angular_components/material_icon/material_icon.dart';
import 'package:angular_router/angular_router.dart';
import 'package:zoran.io/routing/route_paths.dart';
import 'package:zoran.io/services/pipeline_service.dart';

@Component(
  selector: 'pipeline-component',
  templateUrl: 'pipeline_component.html',
  styleUrls: const ['pipeline_component.scss.css'],
  directives: const [
    coreDirectives,
    MaterialButtonComponent,
    MaterialIconComponent,
    AutoFocusDirective,
    MaterialIconComponent,
    MaterialButtonComponent,
    MaterialExpansionPanel,
    MaterialExpansionPanelAutoDismiss,
    MaterialExpansionPanelSet,
    MaterialDialogComponent,
    MaterialInputComponent,
    materialInputDirectives,
    MaterialYesNoButtonsComponent,
    ModalComponent,
    NgModel,
  ]
)
class PipelineComponent implements OnInit {
  final Router _router;
  final PipelineService _service;
  List<PipelineShort> elements;
  bool isCustomToolBeltPanelExpanded = true;

  PipelineComponent(this._router, this._service);

  void viewPipeline(String ele) {
    var url = RoutePaths.pipeline.toUrl(parameters: {uriParam: '$ele'});
    _router.navigate(url);
  }

  void newPipeline() {
    viewPipeline('new');
  }

  bool isCompleted(PipelineStatus s) {
    return s == PipelineStatus.COMPLETED;
  }

  bool isFailed(PipelineStatus s) {
    return s == PipelineStatus.FAILED;
  }

  bool isStopped(PipelineStatus s) {
    return s == PipelineStatus.STOPPED;
  }

  bool isIdle(PipelineStatus s) {
    return s == PipelineStatus.IDLE;
  }

  @override
  void ngOnInit() async {
    this.elements = await _service.getAllPipelineData();
  }

  void run(String id) async {
    _service.run(id);
    var url = RoutePaths.task.toUrl(parameters: {uriParam: '$id'});
    _router.navigate(url);
  }
}