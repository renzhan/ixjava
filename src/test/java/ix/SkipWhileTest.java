/*
 * Copyright 2011-2016 David Karnok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ix;

import java.util.*;

import org.junit.*;

public class SkipWhileTest {

    @Test
    public void normal() {
        Ix<Integer> source = Ix.range(1, 10).skipWhile(new Pred<Integer>() {
            @Override
            public boolean test(Integer v) {
                return v < 6;
            }
        });
        
        IxTestHelper.assertValues(source, 6, 7, 8, 9, 10);
        
        IxTestHelper.assertNoRemove(source);
    }

    @Test
    public void skipAll() {
        Ix<Integer> source = Ix.range(1, 10).skipWhile(new Pred<Integer>() {
            @Override
            public boolean test(Integer v) {
                return true;
            }
        });
        
        IxTestHelper.assertValues(source);
    }

    @Test
    public void skipNone() {
        Ix<Integer> source = Ix.range(1, 10).skipWhile(new Pred<Integer>() {
            @Override
            public boolean test(Integer v) {
                return false;
            }
        });
        
        IxTestHelper.assertValues(source, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    }

    @Test
    public void removeUnskipped() {
        List<Integer> list = IxTestHelper.range(1, 10);
        Ix.from(list).skipWhile(new Pred<Integer>() {
            @Override
            public boolean test(Integer v) {
                return v < 6;
            }
        }).removeAll();
        
        Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 5), list);
    }

}
