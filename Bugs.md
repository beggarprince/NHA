## Current


## Fixed
- Extremely high RAM usage that just keeps incrementing, memory leak somewhere
    - Sol
       - Enemies were made null but the reference to their image was not which kept them in memory
       - this.image = null dereferences it completely so the garbage collector gets rid of it