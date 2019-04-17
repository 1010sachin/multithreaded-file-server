# Multithreaded Web based File Server

## Introduction
Multithreaded Web based File Server is a stand-alone Java program that executes a continually running
web server that serves the files at the provided path. In addition, it also provides runtime metrics that are collected 
and exposed via a [Prometheus](https://prometheus.io/) compatible endpoint.

### Build
In order to build this project you need the following:

* [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Maven 3.1.x](https://maven.apache.org/)

Checkout the project from github
From command line, go to the project source directory
```
$ mvn clean install
```

### Run

From the project's target directory:

```
java -jar multithreaded-server-1.0-SNAPSHOT-jar-with-dependencies.jar 8090 / 3
```
The above jar file takes in three arguments:
1. The port where the web server will be accepting requests.
2. The path of the file or root directory (if you want to list the contents)
3. Number of threads that will be assigned to the application.

Open a web-browser, of your choice and navigate to the link http://localhost:8090. 

### Monitoring

As stated earlier, this application also records run time metrics and provided them on a prometheus compatible endpoint.
In order to view the metrics go to the link http://localhost:8090/metrics.

An example of the metrics collected:

```
# HELP jvm_memory_bytes_used Used bytes of a given JVM memory area.
# TYPE jvm_memory_bytes_used gauge
jvm_memory_bytes_used{area="heap",} 1.208112E7
jvm_memory_bytes_used{area="nonheap",} 8325480.0
# HELP jvm_memory_bytes_committed Committed (bytes) of a given JVM memory area.
# TYPE jvm_memory_bytes_committed gauge
jvm_memory_bytes_committed{area="heap",} 2.57425408E8
jvm_memory_bytes_committed{area="nonheap",} 9633792.0
# HELP jvm_memory_bytes_max Max (bytes) of a given JVM memory area.
# TYPE jvm_memory_bytes_max gauge
jvm_memory_bytes_max{area="heap",} 3.817865216E9
jvm_memory_bytes_max{area="nonheap",} -1.0
# HELP jvm_memory_bytes_init Initial bytes of a given JVM memory area.
# TYPE jvm_memory_bytes_init gauge
jvm_memory_bytes_init{area="heap",} 2.68435456E8
jvm_memory_bytes_init{area="nonheap",} 2555904.0
# HELP jvm_memory_pool_bytes_used Used bytes of a given JVM memory pool.
# TYPE jvm_memory_pool_bytes_used gauge
jvm_memory_pool_bytes_used{pool="Code Cache",} 1694656.0
jvm_memory_pool_bytes_used{pool="Metaspace",} 5939672.0
jvm_memory_pool_bytes_used{pool="Compressed Class Space",} 691152.0
jvm_memory_pool_bytes_used{pool="PS Eden Space",} 1.208112E7
jvm_memory_pool_bytes_used{pool="PS Survivor Space",} 0.0
jvm_memory_pool_bytes_used{pool="PS Old Gen",} 0.0
# HELP jvm_memory_pool_bytes_committed Committed bytes of a given JVM memory pool.
# TYPE jvm_memory_pool_bytes_committed gauge
jvm_memory_pool_bytes_committed{pool="Code Cache",} 2555904.0
jvm_memory_pool_bytes_committed{pool="Metaspace",} 6291456.0
jvm_memory_pool_bytes_committed{pool="Compressed Class Space",} 786432.0
jvm_memory_pool_bytes_committed{pool="PS Eden Space",} 6.7108864E7
jvm_memory_pool_bytes_committed{pool="PS Survivor Space",} 1.1010048E7
jvm_memory_pool_bytes_committed{pool="PS Old Gen",} 1.79306496E8
# HELP jvm_memory_pool_bytes_max Max bytes of a given JVM memory pool.
# TYPE jvm_memory_pool_bytes_max gauge
jvm_memory_pool_bytes_max{pool="Code Cache",} 2.5165824E8
jvm_memory_pool_bytes_max{pool="Metaspace",} -1.0
jvm_memory_pool_bytes_max{pool="Compressed Class Space",} 1.073741824E9
jvm_memory_pool_bytes_max{pool="PS Eden Space",} 1.409286144E9
jvm_memory_pool_bytes_max{pool="PS Survivor Space",} 1.1010048E7
jvm_memory_pool_bytes_max{pool="PS Old Gen",} 2.863661056E9
# HELP jvm_memory_pool_bytes_init Initial bytes of a given JVM memory pool.
# TYPE jvm_memory_pool_bytes_init gauge
jvm_memory_pool_bytes_init{pool="Code Cache",} 2555904.0
jvm_memory_pool_bytes_init{pool="Metaspace",} 0.0
jvm_memory_pool_bytes_init{pool="Compressed Class Space",} 0.0
jvm_memory_pool_bytes_init{pool="PS Eden Space",} 6.7108864E7
jvm_memory_pool_bytes_init{pool="PS Survivor Space",} 1.1010048E7
jvm_memory_pool_bytes_init{pool="PS Old Gen",} 1.79306496E8
# HELP jvm_classes_loaded The number of classes that are currently loaded in the JVM
# TYPE jvm_classes_loaded gauge
jvm_classes_loaded 1066.0
# HELP jvm_classes_loaded_total The total number of classes that have been loaded since the JVM has started execution
# TYPE jvm_classes_loaded_total counter
jvm_classes_loaded_total 1066.0
# HELP jvm_classes_unloaded_total The total number of classes that have been unloaded since the JVM has started execution
# TYPE jvm_classes_unloaded_total counter
jvm_classes_unloaded_total 0.0
# HELP jvm_info JVM version info
# TYPE jvm_info gauge
jvm_info{version="1.8.0_192-b01",vendor="Azul Systems, Inc.",runtime="OpenJDK Runtime Environment",} 1.0
# HELP RequestLatency Request Latency in seconds
# TYPE RequestLatency histogram
RequestLatency_bucket{le="0.005",} 0.0
RequestLatency_bucket{le="0.01",} 0.0
RequestLatency_bucket{le="0.025",} 1.0
RequestLatency_bucket{le="0.05",} 1.0
RequestLatency_bucket{le="0.075",} 1.0
RequestLatency_bucket{le="0.1",} 1.0
RequestLatency_bucket{le="0.25",} 1.0
RequestLatency_bucket{le="0.5",} 1.0
RequestLatency_bucket{le="0.75",} 1.0
RequestLatency_bucket{le="1.0",} 1.0
RequestLatency_bucket{le="2.5",} 1.0
RequestLatency_bucket{le="5.0",} 1.0
RequestLatency_bucket{le="7.5",} 1.0
RequestLatency_bucket{le="10.0",} 1.0
RequestLatency_bucket{le="+Inf",} 1.0
RequestLatency_count 1.0
RequestLatency_sum 0.011187398
# HELP process_cpu_seconds_total Total user and system CPU time spent in seconds.
# TYPE process_cpu_seconds_total counter
process_cpu_seconds_total 0.614776
# HELP process_start_time_seconds Start time of the process since unix epoch in seconds.
# TYPE process_start_time_seconds gauge
process_start_time_seconds 1.555533202403E9
# HELP process_open_fds Number of open file descriptors.
# TYPE process_open_fds gauge
process_open_fds 39.0
# HELP process_max_fds Maximum number of open file descriptors.
# TYPE process_max_fds gauge
process_max_fds 10240.0
# HELP jvm_buffer_pool_used_bytes Used bytes of a given JVM buffer pool.
# TYPE jvm_buffer_pool_used_bytes gauge
jvm_buffer_pool_used_bytes{pool="direct",} 0.0
jvm_buffer_pool_used_bytes{pool="mapped",} 0.0
# HELP jvm_buffer_pool_capacity_bytes Bytes capacity of a given JVM buffer pool.
# TYPE jvm_buffer_pool_capacity_bytes gauge
jvm_buffer_pool_capacity_bytes{pool="direct",} 0.0
jvm_buffer_pool_capacity_bytes{pool="mapped",} 0.0
# HELP jvm_buffer_pool_used_buffers Used buffers of a given JVM buffer pool.
# TYPE jvm_buffer_pool_used_buffers gauge
jvm_buffer_pool_used_buffers{pool="direct",} 0.0
jvm_buffer_pool_used_buffers{pool="mapped",} 0.0
# HELP jvm_memory_pool_allocated_bytes_total Total bytes allocated in a given JVM memory pool. Only updated after GC, not continuously.
# TYPE jvm_memory_pool_allocated_bytes_total counter
# HELP jvm_gc_collection_seconds Time spent in a given JVM garbage collector in seconds.
# TYPE jvm_gc_collection_seconds summary
jvm_gc_collection_seconds_count{gc="PS Scavenge",} 0.0
jvm_gc_collection_seconds_sum{gc="PS Scavenge",} 0.0
jvm_gc_collection_seconds_count{gc="PS MarkSweep",} 0.0
jvm_gc_collection_seconds_sum{gc="PS MarkSweep",} 0.0
# HELP jvm_threads_current Current thread count of a JVM
# TYPE jvm_threads_current gauge
jvm_threads_current 8.0
# HELP jvm_threads_daemon Daemon thread count of a JVM
# TYPE jvm_threads_daemon gauge
jvm_threads_daemon 7.0
# HELP jvm_threads_peak Peak thread count of a JVM
# TYPE jvm_threads_peak gauge
jvm_threads_peak 8.0
# HELP jvm_threads_started_total Started thread count of a JVM
# TYPE jvm_threads_started_total counter
jvm_threads_started_total 8.0
# HELP jvm_threads_deadlocked Cycles of JVM-threads that are in deadlock waiting to acquire object monitors or ownable synchronizers
# TYPE jvm_threads_deadlocked gauge
jvm_threads_deadlocked 0.0
# HELP jvm_threads_deadlocked_monitor Cycles of JVM-threads that are in deadlock waiting to acquire object monitors
# TYPE jvm_threads_deadlocked_monitor gauge
jvm_threads_deadlocked_monitor 0.0
# HELP jvm_threads_state Current count of threads by state
# TYPE jvm_threads_state gauge
jvm_threads_state{state="BLOCKED",} 0.0
jvm_threads_state{state="TIMED_WAITING",} 0.0
jvm_threads_state{state="RUNNABLE",} 5.0
jvm_threads_state{state="TERMINATED",} 0.0
jvm_threads_state{state="NEW",} 0.0
jvm_threads_state{state="WAITING",} 3.0
```

The RequestLatency metrics seen above is the [histogram](https://prometheus.io/docs/concepts/metric_types/#histogram) 
of latencies for each request received (except for requests received on /metrics endpoint).