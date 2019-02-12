import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
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
    MaterialTreeComponent,
    MaterialDialogComponent,
    MaterialButtonComponent,
    ModalComponent
  ]
)
class PipelineViewer implements OnActivate {
  final PipelineService _pipelineService;
  final SelectionModel singleSelection = SelectionModel.single();
  SelectionOptions pipelineHandlers;
  PipelineDetails pipeline;
  bool showBasicDialog = false;
  List<Model> tasks;

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
}
