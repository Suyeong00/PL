module F = Format

let prime_list n = 
  let lst = [] in
  let rec is_prime t num = (*num이 소수인지 판단, idx < last*)
    if t = num then true
    else 
      if num mod t = 0 then false 
      else is_prime (t + 1) num 
  in
  let rec make_prime_lst i lst =
    if i > n then lst
    else 
      if is_prime 2 i then make_prime_lst (i + 1) (i :: lst)
      else make_prime_lst (i + 1) lst
  in
  make_prime_lst 2 lst

let rec len lst =
  match lst with
  | [] -> 0
  | _ :: t ->
  1 + (len t) 
  
let idx lst n = 
  let rec func i tlst =
    match tlst with
    | h :: t -> if i = n then h else func (i + 1) t
    | [] -> failwith "fail"
  in func 0 lst 

let combination fix lst = (*lst의 fix번째 원소와 나머지 각 원소의 합을 리스트에 담아 리턴*)
  let length = len lst in
  let rec func iter result = 
    if iter >= length then result
    else func (iter + 1) ((idx lst iter + idx lst fix) :: result)
  in func (fix + 1) [];;

let rec under_limit plst under_limit_list i limit =
  if i >= len plst then List.rev(under_limit_list)
  else if idx plst i >= limit then List.rev(under_limit_list)
  else under_limit plst ((combination i (plst)) @ under_limit_list) (i + 1) limit;;

let my_combination fix lst cmp lower upper = (*nC2중에서 cmp에 없는 값을 리스트에 담아 리턴*)
  let length = len lst in
  let rec func iter result = 
    if iter >= length then result
    else 
      let sum = idx lst iter + idx lst fix in
      if sum < lower then func (iter + 1) result
      else if sum > upper then func (iter + 1) result
      else 
        if List.mem sum cmp 
        then func (iter + 1) result
        else func (iter + 1) ((sum, ((idx lst fix), (idx lst iter))) :: result)
  in List.rev(func (fix + 1) []);;

let goldbach_list_limit lower upper limit =
  let plst = List.rev(prime_list upper) in
  let ul_list = under_limit plst [] 0 limit in
  let length = len plst in
  let rec func i lst ans =
    if i >= length then ans
    else 
      let new_lst = my_combination i plst lst lower upper in
      func (i + 1) ((List.map (fun x -> fst x) new_lst) @ lst) (new_lst @ ans)
  in func 0 ul_list [];;

let testlist = List.rev(prime_list 20);;

List.iter(fun x -> F.printf "%d " x) (testlist);;
F.printf"\n";;
List.iter(fun x -> F.printf "%d " x) (List.rev((combination 0 (testlist))));; 
F.printf"\n";;
List.iter(fun x -> F.printf "%d " x) (List.rev((combination 1 (testlist))));; 
F.printf"\n";;
List.iter(fun x -> F.printf "%d " x) (List.rev((combination 2 (testlist))));; 

F.printf"\n==============================================\nulimit = ";;
let ulimit = under_limit (List.rev(prime_list 20)) [] 0 5;;
List.iter(fun x -> F.printf "%d " x) ulimit;;
F.printf"\n==============================================\nmy_combination = ";;

let fix = 2 in
let lst = testlist in
let cmp = ulimit in
let lower = 9 in
let upper = 20 in
let combinations = my_combination fix lst cmp lower upper in
  let mlst = List.map (fun x -> fst x) combinations in
  List.iter(fun x -> F.printf "%d " x) mlst;;

F.printf"\n==============================================\ngoldbach_list_limit = ";;
let combinations = goldbach_list_limit 100 100 100 in
  let mlst = List.map (fun x -> fst x) combinations in
  List.iter(fun x -> F.printf "%d " x) mlst;;

let print_tuple_list lst =
  print_string "[";
  List.iter (fun (x, (y, z)) ->
    Printf.printf "(%d, (%d, %d)); " x y z
  ) lst;
  print_string "]\n";;
  
let _ = 
  print_tuple_list (goldbach_list_limit 9 20 5);
  print_tuple_list (goldbach_list_limit 25 70 10);
  print_tuple_list (goldbach_list_limit 100 100 100);
  print_tuple_list (goldbach_list_limit 100 200 19);
  print_tuple_list (goldbach_list_limit 50 500 20);
  print_tuple_list (goldbach_list_limit 1 2000 50);;
(* let _ =
  let _ = F.printf "%d\n" (goldbach_list_limit 9 20 5) in 
  let _ = F.printf "%d\n" (goldbach_list_limit 25 70 10) in 
  let _ = F.printf "%d\n" (goldbach_list_limit 100 100 100) in 
  let _ = F.printf "%d\n" (goldbach_list_limit 100 200 19) in 
  let _ = F.printf "%d\n" (goldbach_list_limit 50 500 20) in 
  F.printf "%d\n" (goldbach_list_limit 1 2000 50) *)
