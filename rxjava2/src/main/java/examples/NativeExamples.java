package examples;

import io.reactivex.Observable;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.AsyncFile;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.reactivex.core.RxHelper;
import io.vertx.reactivex.core.json.ObservableUnmarshaller;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class NativeExamples {

  public void toObservable(Vertx vertx) {
    FileSystem fileSystem = vertx.fileSystem();
    fileSystem.open("/data.txt", new OpenOptions(), result -> {
      AsyncFile file = result.result();
      Observable<Buffer> observable = RxHelper.toObservable(file);
      observable.forEach(data -> System.out.println("Read data: " + data.toString("UTF-8")));
    });
  }

  private Observable<Buffer> getObservable() {
    throw new UnsupportedOperationException();
  }

/*
  public void toReadStream(io.vertx.reactivex.core.Vertx vertx, HttpServerResponse response) {
    Observable<Buffer> observable = getObservable();
    ReadStream<Buffer> readStream = RxHelper.toReadStream(observable);
    Pump pump = Pump.pump(readStream, response);
    pump.start();
  }
*/

/*
  public void observableHandler(Vertx vertx) {
    ObservableHandler<Long> observable = RxHelper.observableHandler();
    observable.subscribe(id -> {
      // Fired
    });
    vertx.setTimer(1000, observable.toHandler());
  }
*/

/*
  public void handlerToSubscriber(Observable<String> observable,
                                  Single<String> single,
                                  Handler<AsyncResult<String>> handler1,
                                  Handler<AsyncResult<String>> handler2) {
    //
    // Subscribe to an Observable
    observable.subscribe(RxHelper.toSubscriber(handler1));

    // Subscribe to a Single
    single.subscribe(RxHelper.toSubscriber(handler2));
  }
*/

/*
  public void observableFuture(Vertx vertx) {
    ObservableFuture<HttpServer> observable = RxHelper.observableFuture();
    observable.subscribe(
        server -> {
          // Server is listening
        },
        failure -> {
          // Server could not start
        }
    );
    vertx.createHttpServer(new HttpServerOptions().
        setPort(1234).
        setHost("localhost")
    ).listen(observable.toHandler());
  }
*/

/*
  public void observableToHandler() {
    Observer<HttpServer> observer = new Observer<HttpServer>() {
      @Override
      public void onNext(HttpServer o) {
      }
      @Override
      public void onError(Throwable e) {
      }
      @Override
      public void onComplete() {
      }
    };
    Handler<AsyncResult<HttpServer>> handler = RxHelper.toFuture(observer);
  }
*/

/*
  public void actionsToHandler() {
    Action1<HttpServer> onNext = httpServer -> {};
    Action1<Throwable> onError = httpServer -> {};
    Action0 onComplete = () -> {};

    Handler<AsyncResult<HttpServer>> handler1 = RxHelper.toFuture(onNext);
    Handler<AsyncResult<HttpServer>> handler2 = RxHelper.toFuture(onNext, onError);
    Handler<AsyncResult<HttpServer>> handler3 = RxHelper.toFuture(onNext, onError, onComplete);
  }

  public void scheduler(Vertx vertx) {
    Scheduler scheduler = RxHelper.scheduler(vertx);
    Observable<Long> timer = Observable.timer(100, 100, TimeUnit.MILLISECONDS, scheduler);
  }

  public void blockingScheduler(Vertx vertx, Observable<Integer> blockingObservable) {
    Scheduler scheduler = RxHelper.blockingScheduler(vertx);
    Observable<Integer> obs = blockingObservable.observeOn(scheduler);
  }

  public void schedulerHook(Vertx vertx) {
    RxJavaSchedulersHook hook = RxHelper.schedulerHook(vertx);
    RxJavaHooks.setOnIOScheduler(f -> hook.getIOScheduler());
    RxJavaHooks.setOnNewThreadScheduler(f -> hook.getNewThreadScheduler());
    RxJavaHooks.setOnComputationScheduler(f -> hook.getComputationScheduler());
  }
*/
  private class MyPojo {
  }

  public void unmarshaller(FileSystem fileSystem) {
    fileSystem.open("/data.txt", new OpenOptions(), result -> {
      AsyncFile file = result.result();
      Observable<Buffer> observable = RxHelper.toObservable(file);
      observable.lift(ObservableUnmarshaller.of(MyPojo.class)).subscribe(
          mypojo -> {
            // Process the object
          }
      );
    });
  }
}
