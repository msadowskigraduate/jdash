import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_forms/angular_forms.dart';
import 'package:angular_router/angular_router.dart';
import 'package:zoran.io/routing/route_paths.dart';
import 'package:zoran.io/services/pipeline_service.dart';
import 'package:zoran.io/services/resource_service.dart';
import 'package:zoran.io/services/zoran_service.dart';

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
    MaterialDropdownSelectComponent,
    formDirectives
  ]
)
class PipelineViewer implements OnActivate {
  final PipelineService _pipelineService;
  final ZoranService _zoranService;
  final Router _router;
  PipelineDetails pipeline;
  bool showBasicDialog = false;
  bool saved = false;
  List<Model> tasks;
  Task currentlySelected = null;
  String userNamesList = "";

  static ItemRenderer responseRenderer = (dynamic res) => res.name;

  List<ResourceResponse> res = [];

  StringSelectionOptions<ResourceResponse> get resourceOptions => StringSelectionOptions<ResourceResponse>(this.res);

  SelectionModel selectionModel = SelectionModel<ResourceResponse>.single(keyProvider: (ResourceResponse response) => response.id);

  PipelineViewer(this._pipelineService, this._router, this._zoranService);

  @override
  Future onActivate(RouterState previous, RouterState current) async {
    tasks = await _pipelineService.getModels();
    res = await _zoranService.getResources();
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

    if(pipeline.listOfSharedUsers != null) {
      userNamesList = pipeline.listOfSharedUsers.reduce((str, str2) => "$str,$str2");
    }
  }

  Future<bool> save() async {
    this.pipeline.resourceId = selectionModel.selectedValues.first.id;
    this.pipeline.listOfSharedUsers = _splitUserNames();
    int result = await _pipelineService.savePipelineDetails(this.pipeline);
    if(result == 200 || result == 201) {
      saved = true;
      _router.navigate('management');
      return true;
    }
    saved = false;
    return false;
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

  void run() async {
    _pipelineService.run(this.pipeline.idDefinition);
    var url = RoutePaths.task.toUrl(parameters: {uriParam: '${this._pipelineService.currentTask.id}'});
    _router.navigate(url);
  }

  void addToParamMap(String key, String value) {
    if(this.currentlySelected != null) {
      this.currentlySelected.parameters[key] = value;
      this.pipeline.tasks.removeWhere((t) => t.handler.clazz == currentlySelected.handler.clazz);
      this.pipeline.tasks.add(currentlySelected);
    }
  }

  List<String> _splitUserNames() {
    return userNamesList.split(",");
  }
}
