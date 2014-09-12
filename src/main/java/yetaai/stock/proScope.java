/*
 * Copyright (c) 2014, Yetaai
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package yetaai.stock;

public class proScope {

    public static void main(String[] arguments) {
        String[] names = {"Elena", "Thomas", "Hamilton", "Suzie", "Phil",
            "Matt", "Alex", "Emma", "John", "James", "Jane", "Emily",
            "Daniel", "Neda", "Aaron", "Kate"};
        int[] times = {341, 273, 278, 329, 445, 402, 388, 275, 243, 334, 412,
            393, 299, 343, 317, 265};

        int fastest, secondfastest;
        int indexfastest, indexsecondfastest;
        fastest = 0;
        secondfastest = 0;
        indexfastest = 0;
        indexsecondfastest = 0;
        for (int i = 0; i < names.length; i++) {
            if (fastest == 0 && secondfastest == 0) {
                fastest = times[i];
            } else if (fastest > 0 && secondfastest == 0) {
                if (times[i] < fastest) {
                    secondfastest = fastest;
                    fastest = times[i];
                    indexfastest = i;

                } else {
                    secondfastest = times[i];
                    indexsecondfastest = i;
                }
            } else {
                if (times[i] < fastest) {
                    secondfastest = fastest;
                    fastest = times[i];

                    indexsecondfastest = indexfastest;
                    indexfastest = i;
                } else if (times[i] >= fastest && times[i] < secondfastest) {
                    secondfastest = times[i];
                    indexsecondfastest = i;
                } else {
                    continue;
                }
            }
        }
        System.out.println("Fastest:" + names[indexfastest] + "  " + times[indexfastest]);
        System.out.println("Second fastest:" + names[indexsecondfastest] + "  " + times[indexsecondfastest]);
    }
}
