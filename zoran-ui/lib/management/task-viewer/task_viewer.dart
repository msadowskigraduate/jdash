import 'package:angular/angular.dart';
import 'package:angular_components/angular_components.dart';
import 'package:angular_router/angular_router.dart';
import 'package:zoran.io/services/pipeline_service.dart';
import 'package:zoran.io/services/zoran_service.dart';

@Component(
  selector: 'task_viewer',
  styleUrls: const ['task_viewer.scss.css'],
  templateUrl: 'task_viewer.html',
  directives: const [
    coreDirectives,
    MaterialProgressComponent,
    MaterialButtonComponent
  ],
  providers: const
  [
    materialProviders
  ],
)
class TaskViewer implements OnActivate {

  final PipelineService _service;

  PipelineTask task;

  TaskViewer(this._service);

  @override
  void onActivate(RouterState previous, RouterState current) {
    this.task = _service.currentTask;
  }

  void checkState() {
    _service.check(task.idTask);
  }

  void download() {
    _service.download(task.definition.resourceId);
  }
}