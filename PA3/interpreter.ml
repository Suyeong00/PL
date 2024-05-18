module F = Format

let rec len lst =
  match lst with
  | [] -> 0
  | _ :: t -> 1 + (len t)

let get_eval_list func e_list g s =
  List.map (fun expr -> func expr g s) e_list

let combine_list l1 l2 =
  List.combine l1 l2

let update_mem s tuple_list = 
  List.fold_left (fun acc (arg, arg_val) -> Store.add arg arg_val acc) s tuple_list

let rec interp_expr (e: Ast.expr) (g: FStore.t) (s: Store.t) : Value.t =
  match e with
  | Ast.Num n -> Value.NumV n
  | Ast.Add (e1, e2) ->
    let Value.NumV n1 = interp_expr e1 g s in
    let Value.NumV n2 = interp_expr e2 g s in
    Value.NumV (n1 + n2)
  | Ast.Sub (e1, e2) ->
    let Value.NumV n1 = interp_expr e1 g s in
    let Value.NumV n2 = interp_expr e2 g s in
    Value.NumV (n1 - n2)
  | Ast.Id x ->
    if Store.mem x s then Store.find x s
    else failwith ("Free identifier: " ^ x)
  | Ast.LetIn (x, e1, e2) -> 
    let n1 = interp_expr e1 g s in
    let nm = Store.add x n1 s in
    interp_expr e2 g nm
  | Ast.Call (x, e_list) ->
    if FStore.mem x g then
      let (args, expr) = FStore.find x g in
      let e_num = List.length e_list in
      let args_num = List.length args in
      if args_num = e_num then
        let arg_vals = get_eval_list interp_expr e_list g s in
        let tuple_list = combine_list args arg_vals in
        let nm = update_mem s tuple_list in
        interp_expr expr g nm
      else
        failwith ("The number of arguments of " ^ x ^ 
        " mismatched: Required: " ^ string_of_int args_num ^ 
        ", Actual: " ^ string_of_int e_num)
    else
      failwith ("Undefined function: " ^ x)

let interp_fundef (d: Ast.fundef) (g: FStore.t) : FStore.t =
  match d with
  | Ast.FunDef (x, args, expr) -> FStore.add x (args, expr) g

let interp (p: Ast.prog) : Value.t =
  match p with
  | Ast.Prog (deff_list, expr) ->
      let g = List.fold_left (fun acc deff_list -> interp_fundef deff_list acc) FStore.empty deff_list in
      interp_expr expr g Store.empty
