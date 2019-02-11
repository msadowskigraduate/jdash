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
    MaterialTreeComponent
  ]
)
class PipelineViewer implements OnActivate {
  final PipelineService _pipelineService;
  final Router _router;
  final SelectionModel singleSelection = SelectionModel.single();
  SelectionOptions pipelineHandlers;
  PipelineShort pipeline;

  PipelineViewer(this._pipelineService, this._router);

  @override
  Future onActivate(RouterState previous, RouterState current) async {
    String uri = getUrl(current.parameters);
    pipeline = await _pipelineService.getPipelineDetails(uri);
  }


}
