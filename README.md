# jfr_vs_ap

This is a simple Java application designed to test and compare the outputs of Java Flight Recorder (JFR) and Async-Profiler.

**Note:** JFR files might contain sensitive metadata that can be difficult to safely share. This application allows you to generate these files locally, enabling safe inspection and comparison of profiling data.

## Getting Started

### Prerequisites

- Ensure you have Java Development Kit (JDK) installed on your system.
- Download Async-Profiler from its [official repository](https://github.com/jvm-profiling-tools/async-profiler).

### Setup

1. **Clone the Repository**

   Clone this repository to your local machine.

   ```sh
   git clone <repository-url>
   cd jfr_vs_ap
   ```

2. **Set Up Directories**

   Ensure the necessary directories are prepared:

   ```sh
   mkdir -p OUTPUTS/jfr
   ```

### Compilation and Execution

1. **Run with Java Flight Recorder**

   Execute the application using JFR:

   ```sh
   ./run-with-jfr.sh
   ```

   This will generate JFR recording files in the `OUTPUTS/jfr` directory.

2. **Run with Async-Profiler**

   Execute the application using Async-Profiler:

   ```sh
   ./run-async-profiler.sh /path/to/libasyncProfiler.dylib
   ```

   Replace `/path/to/libasyncProfiler.dylib` with the correct path to the `libasyncProfiler.dylib` (for linux this is a .so file) file from your Async-Profiler download.

### Comparing Outputs

- Inspect the outputs located in `outputs/jfr` and `outputs/ap`.
