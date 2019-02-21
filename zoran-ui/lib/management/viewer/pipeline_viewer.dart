import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:angular_router/angular_router.dart';
import 'package:zoran.io/routing/route_paths.dart';
import 'package:zoran.io/services/pipeline_service.dart';

@Component(
  selector: 'pipeline-viewer',
  templateUrl: 'pipeline_viewer.html',
  directives: const [
    coreDirectives,
    AutoDismissDirective,
    AutoFocusDirective,
    MaterialListComponent,
    MaterialListItemComponent,
    MaterialDialogComponent,
    MaterialButtonComponent,
    ModalComponent,
    materialInputDirectives,
    MaterialMultilineInputComponent,
    formDirectives
  ]
)
class PipelineViewer implements OnActivate {
  final PipelineService _pipelineService;
  PipelineDetails pipeline;
  bool showBasicDialog = false;
  List<Model> tasks;

  Task currentlySelected = null;

  PipelineViewer(this._pipelineService);

  @override
  Future onActivate(RouterState previous, RouterState current) async {
    tasks = await _pipelineService.getAllModels();
    String uri = getUrl(current.parameters);
    if(uri == 'new') {
      this.pipeline = PipelineDetails.init();
      return;
    }

    pipeline = await _pipelineService.getPipelineDetails(uri);

    if(pipeline == null) {
      this.pipeline = PipelineDetails.init();
      return;
    }
  }

  Future<bool> save() async {
    int result = await _pipelineService.savePipelineDetails(this.pipeline);
    return result == 200 || result == 201;
  }

  void addHandlerToPipeline(Model task) {
    Task t = new Task();
    t.order = pipeline.tasks != null ? pipeline.tasks.length : 0;
    t.handler = task;
    t.parameters = {};
    pipeline.tasks.add(t);
  }

  void select(Task t) {
    this.currentlySelected = t;
  }

  void addToParamMap(String key, String value) {
    if(this.currentlySelected != null) {
      this.currentlySelected.parameters[key] = value;
    }
  }
}
