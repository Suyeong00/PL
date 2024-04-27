module F = Format

let factor_list n =
  let rec func i num lst =
    if i > num then lst
    else 
      if num mod i = 0 then func (i + 1) num (i :: lst)
      else func (i + 1) num lst
    in
    func 2 n [];;

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

let rec isin comp_list my_list i =
  let length = len comp_list in
  if i >= length then true
  else if List.mem (idx comp_list i) my_list then false
  else isin comp_list my_list (i + 1)

let phi n = 
  let my_list = factor_list n in
  let rec func i res = 
    let com_list = factor_list i in
    if i >= n then res
    else
      if isin com_list my_list 0 then func (i + 1) res + 1
      else func (i + 1) res
    in (func 2 0) + 1

let _ =
  let _ = F.printf "%d\n" (phi 4) in 
  let _ = F.printf "%d\n" (phi 9) in 
  let _ = F.printf "%d\n" (phi 10)in 
  let _ = F.printf "%d\n" (phi 17) in 
  F.printf "%d\n" (phi 30);;